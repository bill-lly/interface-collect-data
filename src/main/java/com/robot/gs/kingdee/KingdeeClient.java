package com.robot.gs.kingdee;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.kingdee.processor.KingdeeDataProcessor;
import com.robot.gs.ticket.http.kingdee.config.ServiceGoConfig;
import com.robot.gs.ticket.http.kingdee.handler.RecordHandler;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.common.ApiNameEnum.*;

@Slf4j
public class KingdeeClient {
    private final ServiceGoConfig serviceGoConfig;
    private final KingdeeDataProcessor dataProcessor;
    private final ApiNameEnum apiObjectName;
    private Boolean isFirst = true;

    public KingdeeClient(Properties properties, ApiNameEnum apiObjectName,
                         KingdeeDataProcessor dataProcessor) {
        serviceGoConfig = new ServiceGoConfig();
        serviceGoConfig.setScheme(properties.getProperty("scheme"));
        serviceGoConfig.setHost(properties.getProperty("host"));
        serviceGoConfig.setPathSegment1(properties.getProperty("pathSegment1"));
        serviceGoConfig.setPathSegment2(properties.getProperty("pathSegment2"));
        serviceGoConfig.setSysId(properties.getProperty("sysId"));
        serviceGoConfig.setViewDetailServId(properties.getProperty("viewDetailServId"));
        serviceGoConfig.setNoListServId(properties.getProperty("noListServId"));
        serviceGoConfig.setSessionIdServId(properties.getProperty("sessionIdServId"));
        serviceGoConfig.setSessionIdAcctid(properties.getProperty("sessionIdAcctid"));
        serviceGoConfig.setSessionIdUserName(properties.getProperty("sessionIdUserName"));
        serviceGoConfig.setSessionIdPassword(properties.getProperty("sessionIdPassword"));
        serviceGoConfig.setSessionIdLcid(Integer.parseInt(properties.getProperty("sessionIdLcid")));
        this.dataProcessor = dataProcessor;
        this.apiObjectName = apiObjectName;
    }

    public void getAndSaveData(Statement stmt,
                               LocalDate conditionDate) throws SQLException, InterruptedException {
        RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
        //获得所有FBillNoList or FNumberList
        List<String> stringNoList = recordHandler.getNoList(apiObjectName, conditionDate);
        //遍历NoList，调用ViewDetail接口，获得ViewDetail结果集
        //获取总行数
        int totalCount = stringNoList.size();
        //定义每页的条数
        int pageSize = 20;
        //计算循环次数  Math.ceil为向上取整
        int pages = (int) Math.ceil(totalCount / (pageSize * 1.0));
        //批处理集合
        List<String> batch = new ArrayList<>();
        int startViewDetailList = 0;
        for (int pagesIndex = 1; pagesIndex <= pages; pagesIndex++) {
            List<String> dataList = recordHandler.getFixViewDetailList(apiObjectName, stringNoList, pageSize, startViewDetailList, totalCount);
            startViewDetailList = startViewDetailList + pageSize;
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

    private void addData(List<String> batch, List<String> dataList) {
        batch.addAll(dataList);
        dataProcessor.increase(dataList.size());
    }


    private void flushIfNeed(Statement stmt, List<String> batch) throws SQLException, InterruptedException {
        if (batch.size() < 20) {
            return;
        }
        flush(stmt, batch);
    }

    private void flush(Statement stmt, List<String> batch) throws SQLException {
        dataProcessor.save(stmt, batch, isFirst);
        isFirst = false;
        batch.clear();
    }
}
