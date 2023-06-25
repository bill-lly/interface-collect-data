package com.robot.gs.mes;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.mes.config.ServiceGoConfig;
import com.robot.gs.ticket.http.mes.handler.RecordHandler;
import com.robot.gs.mes.dateProcessor.MesDataProcessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

@Slf4j
public class MesClient {
    private final ServiceGoConfig serviceGoConfig;
    private final MesDataProcessor dataProcessor;
    private final ApiNameEnum apiObjectName;
    private Boolean isFirst = true;

    public MesClient(Properties properties, ApiNameEnum apiObjectName, MesDataProcessor dataProcessor) {
        this.serviceGoConfig = new ServiceGoConfig();
        serviceGoConfig.setUrl(properties.getProperty("url"));
        serviceGoConfig.setApi_id(properties.getProperty("api_id"));
        serviceGoConfig.setApi_key(properties.getProperty("api_key"));
        serviceGoConfig.setSysId(properties.getProperty("sysId"));
        serviceGoConfig.setServIdModelNumber(properties.getProperty("servIdModelNumber"));
        serviceGoConfig.setServIdByModelNumber(properties.getProperty("servIdByModelNumber"));
        serviceGoConfig.setServIdByDate(properties.getProperty("servIdByDate"));
        this.apiObjectName = apiObjectName;
        this.dataProcessor = dataProcessor;
    }

    public void getAndSaveData(Statement stmt, LocalDate conditionDate) throws SQLException {

        RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
        //定义每页的条数
        int pageSize = 30;
        // 批处理集合
        JSONArray batch = new JSONArray();
        int startOffset = 0;
        int total = 0;
        boolean flag = true;
        while (flag) {
            String res = recordHandler.queryProcessReportByDate(conditionDate.toString(), conditionDate.toString(),
                    String.valueOf(startOffset), String.valueOf(pageSize));
            JSONObject jsonObject = JSONObject.parseObject(res);
            total = jsonObject.getIntValue("total");
            int size = jsonObject.getIntValue("size");
            if (total == 0) {
                flag = false;
                log.info("Today have 0 rows data");
                log.info("Download " + dataProcessor.getCount() + " rows data");
                return;
            }
            JSONArray dataList = jsonObject.getJSONArray("data");
            addData(batch, dataList);
            flushIfNeed(stmt, batch);
            startOffset += size;
            if (startOffset >= total) {
                flag = false;
                flush(stmt, batch);
            }
        }

        log.info("Download " + dataProcessor.getCount() + " rows data");
    }

    private void addData(JSONArray batch, JSONArray dataList) {
        batch.addAll(dataList);
        dataProcessor.increase(dataList.size());
    }

    private void flush(Statement stmt, JSONArray batch) throws SQLException {
        dataProcessor.save(stmt, batch, isFirst);
        isFirst = false;
        batch.clear();
    }

    private void flushIfNeed(Statement stmt, JSONArray batch) throws SQLException {
        if (batch.size() < 2000) {
            return;
        }
        flush(stmt, batch);
    }
}
