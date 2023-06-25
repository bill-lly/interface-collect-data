package com.robot.gs.udesk.integration;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.common.CommonDataProcessor;
import com.robot.gs.common.DataProcessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.robot.gs.udesk.integration.Helper.value;

@Slf4j
public class ErrorCodeProcessor extends CommonDataProcessor {

    private final ErrorCodeConverter converter = new ErrorCodeConverter();
    private static String dbName;
    private static String tbName;

    public ErrorCodeProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
                              boolean hasPartition) {
        super(apiNameEnum, dbName, tbName, startDate, hasPartition);
        this.dbName=dbName;
        this.tbName=tbName;
    }

    @Override
    protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
        return null;
    }

    @Override
    public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {
        if (dataList.size() != 0) {
            StringBuilder builder = new StringBuilder(10240);
            if (isFirst) {
                builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", this.dbName, this.tbName));
            } else {
                builder.append(String.format("INSERT INTO TABLE %s.%s\n", this.dbName, this.tbName));
            }
            builder.append("PARTITION (pt)\n")
                    .append("SELECT INLINE (ARRAY ( ");
            for (Map<String, Object> data : dataList) {
                ErrorCode e = converter.from(data);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                builder.append("STRUCT (")
                        .append(e.getId()).append(", '")
                        .append(e.getErrorCode()).append("', '")
                        .append(value(e.getErrorName())).append("', '")
                        .append(value(e.getHealthLevel())).append("', '")
                        .append(value(e.getOpLevel())).append("', '")
                        .append(value(e.getApplicableModel())).append("', '")
                        .append(value(e.getRemarks())).append("', '")
                        .append(e.getCreateTime()).append("', '")
                        .append(e.getUpdateTime()).append("', '")
                        .append(e.getCreateUserId()).append("', '")
                        .append(value(e.getCreateUserEmail())).append("', '")
                        .append(e.getUpdateUserId()).append("', '")
                        .append(value(e.getUpdateUserEmail())).append("', '")
                        .append(e.getOwnerId()).append("', '")
                        .append(value(e.getOwnerResult())).append("' ");
                if (hasPartition == true) {
                    builder.append(",'" + formatter.format(startDate) + "'");
                }
                builder.append(")").append(",");
            }
            if (builder.toString().endsWith(",")) {
                builder.delete(builder.length() - 1, builder.length())
                        .append("))");
            }
            try {
                stmt.execute(builder.toString());
            } catch (RuntimeException e) {
                log.error("Syntax parsed sql failed");
                throw new RuntimeException("Save error code data failed");
            }
        }
    }
}
