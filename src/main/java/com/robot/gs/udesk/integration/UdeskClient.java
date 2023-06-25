package com.robot.gs.udesk.integration;

import static com.robot.gs.udesk.integration.Helper.getInt;

import com.robot.gs.common.DataProcessor;
import com.robot.gs.ticket.http.udesk.config.ServiceGoConfig;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.udesk.handler.RecordHandler;
import com.robot.gs.ticket.http.udesk.model.RecordQueryScrollSearchModel;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import com.robot.gs.ticket.http.udesk.util.DataUtil;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * https://www.udesk.cn/doc/sercive/data/#_32
 */
@Slf4j
public class UdeskClient {
  private final ServiceGoConfig serviceGoConfig;
  private final DataProcessor dataProcessor;
  private final ApiNameEnum apiObjectName;
  private Boolean isFirst = true;

  public UdeskClient(Properties properties, ApiNameEnum apiObjectName,
      String filterIdName, DataProcessor dataProcessor) {
    serviceGoConfig = new ServiceGoConfig();
    serviceGoConfig.setApiToken(properties.getProperty("api.token"));
    serviceGoConfig.setApiUrlPrefix(properties.getProperty("api.url.prefix"));
    serviceGoConfig.setUserId(properties.getProperty("user.id"));
    serviceGoConfig.setEmail(properties.getProperty("email"));
    serviceGoConfig.setFilterId(getInt(properties, filterIdName,26287));

    this.dataProcessor = dataProcessor;
    this.apiObjectName = apiObjectName;
  }

  public void getAndSaveData(Statement stmt,
      ConditionFactory conditionFactory) throws SQLException, InterruptedException {
    RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
    long scrollId = 0;
    List<Map<String, Object>> batch = new ArrayList<>();
    int retryCount=0;
    while (true) {
      RecordQueryScrollSearchModel querySearchModel =
          RecordQueryScrollSearchModel.builder()
              .filterId(serviceGoConfig.getFilterId())
              .judgeStrategy(1)
              .scrollId(scrollId)
              .pageSize(100)
              .conditionList(null == conditionFactory ? null : conditionFactory.create())
              .objectApiName(apiObjectName.getName())
              .build();
      ResultModel queryList = recordHandler.queryScrollListBySearch(querySearchModel);

      try {
        if (null == queryList) {
          log.error("Query result is null");
          throw new RuntimeException("failed to query the data from udesk client");
        }
        if (200 != queryList.getCode()) {
          log.error("Can not get data from udesk" + queryList.getMessage());
          throw new RuntimeException("failed to query the data from udesk client");
        }
      } catch (RuntimeException e) {
        retryCount++;
        log.error("retry qurey："+retryCount+" times");
        Thread.sleep(5000);
        if (retryCount>100) throw new RuntimeException("It has been retried "+retryCount+"th times ,reach the upper " +
                "limit");
        continue;
      }

      List<Map<String, Object>> dataList = DataUtil.convertToList(queryList.getData());
      if (dataList == null || dataList.size() == 0) {
        log.info("Download " + dataProcessor.getCount() + " rows data");
        flush(stmt, batch);
        return;
      }
      addData(batch, dataList);
      flushIfNeed(stmt, batch);
      scrollId = getNextScrollId(dataList);
    }
  }

  private void addData(List<Map<String, Object>> batch, List<Map<String, Object>> dataList) {
    batch.addAll(dataList);
    dataProcessor.increase(dataList.size());
  }


  private void flushIfNeed(Statement stmt, List<Map<String, Object>> batch) throws SQLException, InterruptedException {
    if (batch.size() < 1000) {
      return;
    }
    flush(stmt, batch);
    //避免udesk这边限流的问题
    Thread.sleep(200);
  }

  private void flush(Statement stmt, List<Map<String, Object>> batch) throws SQLException {
    dataProcessor.save(stmt, batch, isFirst);
    isFirst = false;
    batch.clear();
  }

  private Long getNextScrollId(List<Map<String, Object>> dataList) {
    Collections.sort(dataList, new Comparator<Map<String, Object>>() {
      @Override
      public int compare(Map<String, Object> o1, Map<String, Object> o2) {
        String id1 = o1.get("id").toString();
        String id2 = o2.get("id").toString();
        return id2.compareTo(id1);
      }
    });
    String id = dataList.get(0).get("id").toString();
    return Long.parseLong(id);
  }

}
