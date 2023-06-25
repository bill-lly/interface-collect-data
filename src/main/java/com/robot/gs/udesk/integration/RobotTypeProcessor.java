package com.robot.gs.udesk.integration;

import static com.robot.gs.udesk.integration.Helper.value;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.robot.gs.common.DataProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RobotTypeProcessor extends DataProcessor {
  private final RobotTypeConverter converter = new RobotTypeConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_robot_type";

  public RobotTypeProcessor(LocalDate startDate) {
    super(startDate);
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst)
      throws SQLException {
    DateTimeFormatter ptDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("SELECT INLINE (ARRAY ( ");
    for (Map<String, Object> data : dataList) {
      RobotType e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getNote())).append("', '")
          .append(value(e.getProductSn())).append("', ")
          .append(e.getRobotFamilyId()).append(", '")
          .append(value(e.getRobotFamilyCode())).append("', '")
          .append(value(e.getMachineVersion())).append("', '")

          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(e.getCreateTime().format(dateTimeFormatter)).append("', '")
          .append(e.getUpdateTime().format(dateTimeFormatter)).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
