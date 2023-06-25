package com.robot.gs.mes.dateProcessor;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;

import com.robot.gs.mes.bean.MaterialWHKingdee;
import com.robot.gs.mes.converter.MaterialWHKingdeeConverter;
import lombok.extern.slf4j.Slf4j;


import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Slf4j
public class MaterialWHKingdeeProcessor extends MesCommonDataProcessor {

    private final MaterialWHKingdeeConverter converter = new MaterialWHKingdeeConverter();

    public MaterialWHKingdeeProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
                                      boolean hasPartition) {
        super(apiNameEnum, dbName, tbName, startDate, hasPartition);
    }

    @Override
    protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
        return null;
    }

    @Override
    public void save(Statement stmt, JSONArray dataList, Boolean isFirst) throws SQLException {
        StringBuilder builder = new StringBuilder(10240);
        if (isFirst) {
            builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
        } else {
            builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
        }
        builder.append("PARTITION (pt)\n")
                .append("SELECT INLINE (ARRAY ( ");
        for (Object data : dataList) {
            System.out.println(data.toString());
            MaterialWHKingdee e = converter.from(JSONObject.parseObject(data.toString()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getModel_number()).append("', '")
                    .append(e.getSn()).append("', '")
                    .append(e.getProduct_number()).append("', '")
                    .append(e.getKingdee_mo_number()).append("', '")
                    .append(e.getDatemodified()).append("', '")
                    .append(e.getKingdee_stockin_number()).append("', '")
                    .append(e.getDatecreated()).append("', '")
                    .append(e.getWorkshop_number()).append("', '")
                    .append(e.getCreatedbyname()).append("'");
            if (hasPartition) {
                builder.append(",'").append(formatter.format(startDate)).append("'");
            }
            builder.append(")").append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.delete(builder.length() - 1, builder.length())
                    .append("))");
        }
        try {
            stmt.execute(builder.toString());
            log.info(apiNameEnum + " Save data successfully");
        } catch (RuntimeException e) {
            log.error("Syntax parsed sql failed");
            throw new RuntimeException("Save "+apiNameEnum+" data failed");
        }

    }
}
