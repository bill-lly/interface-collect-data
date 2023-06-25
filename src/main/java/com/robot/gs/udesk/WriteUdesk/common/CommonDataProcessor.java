package com.robot.gs.udesk.WriteUdesk.common;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.udesk.handler.RecordHandler;
import com.robot.gs.common.DataProcessor;
import com.robot.gs.ticket.http.udesk.model.RecordUpdateModel;
import com.robot.gs.ticket.http.udesk.model.common.FieldData;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import lombok.extern.slf4j.Slf4j;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class CommonDataProcessor extends DataProcessor {
    protected ApiNameEnum apiNameEnum;
    public CommonDataProcessor(ApiNameEnum apiNameEnum) {
        this.apiNameEnum = apiNameEnum;
    }

    public void mappingAndPutData(RecordHandler recordHandler, ResultSet resultSet, ApiNameEnum apiNameEnum) throws SQLException {
        log.info("mapping start");
        int recordId = resultSet.getInt("record_id");
        List<FieldData> dataList = null;
        try {
            dataList = mapping(resultSet, apiNameEnum);
        } catch (SQLException e) {
            log.error("mapping failed");
            throw new RuntimeException(e);
        }
        log.info("mapping succeed");
        log.info("start put data");
        put(recordHandler,dataList,apiNameEnum,recordId);
        log.info("put data completed");
    };
    public abstract List<FieldData> mapping(ResultSet resultSet, ApiNameEnum apiNameEnum) throws SQLException;
    public void put(RecordHandler recordHandler, List<FieldData> dataList, ApiNameEnum apiNameEnum,int recordId) {
        RecordUpdateModel recordUpdateModel = RecordUpdateModel.builder()
                .objectApiName(apiNameEnum.getName())
                .id(recordId)
                .fieldDataList(dataList)
                .build();
        ResultModel update = recordHandler.update(recordUpdateModel);
        if (update.getCode()!=200){
            log.error("recordId: "+ recordId+" update failed");
            throw new RuntimeException(recordId+" update failed, error code:"+update.getCode()+", message:"+update.getMessage());
        }else {
            log.info("recordId: "+ recordId+" update successed");
        }
    };

    public Map<String, String> from(ResultSet resultSet) throws SQLException {
        Map<String, String> map = new HashMap<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            map.put(metaData.getColumnName(i), resultSet.getString(i));
        }
        return map;
    }

    private Object getColumnValue(ResultSet resultSet, int columnIndex) throws SQLException {
        switch (resultSet.getMetaData().getColumnTypeName(columnIndex)) {
            case "TINYINT":
            case "SMALLINT":
            case "MEDIUMINT":
            case "BIGINT":
                return resultSet.getLong(columnIndex);
            case "CHAR":
            case "VARCHAR":
            case "TINYTEXT":
            case "TEXT":
            case "MEDIUMTEXT":
            case "LONGTEXT":
            case "JSON":
            case "ENUM ":
                return resultSet.getString(columnIndex);
            case "BIT":
                return resultSet.getBoolean(columnIndex);
            case "FLOAT":
                return resultSet.getFloat(columnIndex);
            case "DOUBLE":
                return resultSet.getDouble(columnIndex);
            case "DECIMAL":
                return resultSet.getBigDecimal(columnIndex);
            case "DATE":
                return resultSet.getDate(columnIndex);
            case "TIME":
                return resultSet.getTime(columnIndex);
            case "DATETIME":
                return resultSet.getTimestamp(columnIndex);
            case "TIMESTAMP":
                return resultSet.getTimestamp(columnIndex);
            case "YEAR":
                return resultSet.getString(columnIndex).substring(0,4);
            case "BINARY":
            case "VARBINARY":
                return resultSet.getBytes(columnIndex);
            case "TINYBLOB":
            case "BLOB":
            case "MEDIUMBLOB":
            case "LONGBLOB":
                return resultSet.getBlob(columnIndex);
            case "GEOMETRY":
            case "POINT":
            case "LINESTRING":
            case "POLYGON":
                return resultSet.getString(columnIndex);
            default:
                return null;
        }
    }

}
