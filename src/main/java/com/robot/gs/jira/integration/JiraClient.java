package com.robot.gs.jira.integration;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.DataProcessor;
import com.robot.gs.ticket.http.jira.config.ServiceGoConfig;
import com.robot.gs.ticket.http.jira.handler.RecordHandler;
import com.robot.gs.ticket.http.udesk.util.DataUtil;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class JiraClient {
    private final ServiceGoConfig serviceGoConfig;
    private final DataProcessor dataProcessor;
    private final ApiNameEnum apiObjectName;
    private Boolean isFirst = true;

    public JiraClient(Properties properties, ApiNameEnum apiObjectName, DataProcessor dataProcessor
    ) {
        serviceGoConfig = new com.robot.gs.ticket.http.jira.config.ServiceGoConfig();
        serviceGoConfig.setApiUrlPrefix(properties.getProperty("api.url.prefix"));
        serviceGoConfig.setUserName(properties.getProperty("username"));
        serviceGoConfig.setPassWord(properties.getProperty("password"));
        serviceGoConfig.setApiUrlWorkflow(properties.getProperty("api.url.workflow"));
        serviceGoConfig.setApiUrlWorkflowStatus(properties.getProperty("api.url.workflow.status"));

        this.dataProcessor = dataProcessor;
        this.apiObjectName = apiObjectName;
    }

    public void getAndSaveData(Statement stmt,
                               LocalDate conditionDate) throws IOException, SQLException {
        RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
        LocalDate plusDay = conditionDate.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String queryStartDate = formatter.format(conditionDate);
        String queryEndDate = formatter.format(plusDay);
        String jql = "project in(" + apiObjectName.getName()
                + ") and updated > '" + queryStartDate
                + "' and updated < '" + queryEndDate
                + "'";
        //获取总行数
        int totalCount = recordHandler.getTotal(jql);
        //定义每页的条数
        int pageSize = 100;
        //计算循环次数，即总页数  Math.ceil为向上取整
        int pages = (int) Math.ceil(totalCount/(pageSize*1.0));
        //批处理集合
        List<Map<String, Object>> batch = new ArrayList<>();

        for(int pagesIndex = 1; pagesIndex <= pages; pagesIndex++){
            SearchResult searchResult = recordHandler.queryList(jql, pageSize,(pagesIndex-1)*pageSize, null);
            if (null == searchResult) {
                log.error("Query result is null");
                throw new RuntimeException("failed to query the data from jira client");
            }

            List<Map<String, Object>> dataList = DataUtil.convertToList(searchResult.getIssues());
            if (dataList.size() < pageSize) {
                addData(batch, dataList);
                flush(stmt, batch);
                log.info("Download " + dataProcessor.getCount() + " rows data");
                return;
            }
            addData(batch, dataList);
            flushIfNeed(stmt, batch);
        }
    }

    private void addData(List<Map<String, Object>> batch, List<Map<String, Object>> dataList) {
        batch.addAll(dataList);
        dataProcessor.increase(dataList.size());
    }

    private void flushIfNeed(Statement stmt, List<Map<String, Object>> batch) throws SQLException {
        if (batch.size() < 1000) {
            return;
        }
        flush(stmt, batch);
    }

    private void flush(Statement stmt, List<Map<String, Object>> batch) throws SQLException {
        dataProcessor.save(stmt, batch, isFirst);
        isFirst = false;
        batch.clear();
    }

}
