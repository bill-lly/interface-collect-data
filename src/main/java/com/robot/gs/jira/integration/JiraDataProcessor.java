package com.robot.gs.jira.integration;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.google.gson.reflect.TypeToken;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.common.CommonDataProcessor;
import com.robot.gs.ticket.http.jira.config.ServiceGoConfig;
import com.robot.gs.ticket.http.jira.handler.RecordHandler;
import com.robot.gs.ticket.http.jira.result.WorkFlowStatus;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class JiraDataProcessor extends CommonDataProcessor {
  private final JiraConverter converter = new JiraConverter();
  private ApiNameEnum apiName = null;
  public JiraDataProcessor(ApiNameEnum apiNameEnum,
                           String dbName, String tbName,
                           LocalDate startDate, boolean hasPartition) {
    super(apiNameEnum, dbName, tbName, startDate, hasPartition);
    this.apiName = apiNameEnum;
  }

  @Override
  protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
    switch (apiNameEnum) {
      case JIRA_SELLSERVIC:
      case JIRA_SOFTREQ:
      case JIRA_ZC:
      case JIRA_QD:
        return new CommonDataConverter(JiraInfo.class);
      default:
        throw new RuntimeException("no matching converter");
    }
  }

  protected RecordHandler getRecordHandler() throws IOException {
    Properties properties = loadProperties("/jira.properties");
    ServiceGoConfig serviceGoConfig = new ServiceGoConfig();
    serviceGoConfig.setApiUrlPrefix(properties.getProperty("api.url.prefix"));
    serviceGoConfig.setUserName(properties.getProperty("username"));
    serviceGoConfig.setPassWord(properties.getProperty("password"));
    serviceGoConfig.setApiUrlWorkflow(properties.getProperty("api.url.workflow"));
    serviceGoConfig.setApiUrlWorkflowStatus(properties.getProperty("api.url.workflow.status"));
    serviceGoConfig.setApiUrlChangelogRecord(properties.getProperty("api.url.changelog"));
    RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
    return recordHandler;
  }

  public Map<String, String> getWorkFlowStatus(RecordHandler recordHandler) {
    Map<String, String> map = new HashMap<>();
    String workFlowStatusRes = recordHandler.queryWorkFlowStatus();
    List<WorkFlowStatus> workFlowStatusList = gson.fromJson(workFlowStatusRes, new TypeToken<List<WorkFlowStatus>>() {}.getType());
    workFlowStatusList.forEach(workFlowStatus -> {
      map.put(workFlowStatus.getId(), workFlowStatus.getName());
    });
    return map;
  }

  public void save(Statement stmt, List dataList, Boolean isFirst) throws SQLException {
    StringBuilder builder = new StringBuilder(10240);
    RecordHandler recordHandler = null;
    try {
      recordHandler = getRecordHandler();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Map<String, String> workFlowStatusMap = getWorkFlowStatus(recordHandler);

    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("PARTITION (pt)\n")
            .append("SELECT INLINE (ARRAY ( ");
    for (Object data : dataList) {
      JiraInfo e = converter.from((Issue) data, apiName, recordHandler, workFlowStatusMap);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      builder.append("STRUCT ('");
      Field[] fields = e.getClass().getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
        Object v = getFieldValue(fields[i], e);
        builder.append(v);
        if (i == fields.length - 1) {
          if (hasPartition == true) {
            builder.append("','" + formatter.format(startDate));
          }
          builder.append("'), ");
        } else {
          builder.append("', '");
        }
      }
    }
    if (builder.toString().endsWith(", ")) {
      builder.delete(builder.length() - 2, builder.length())
              .append("))");
      try {
        //log.info(builder.toString());
        stmt.execute(builder.toString());
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }
}
