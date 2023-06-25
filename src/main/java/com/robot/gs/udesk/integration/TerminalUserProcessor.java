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
public class TerminalUserProcessor extends DataProcessor {
  private final TerminalUserConverter converter = new TerminalUserConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_terminal_user";

  public TerminalUserProcessor(LocalDate startDate) {
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
      TerminalUser e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getAddress())).append("', '")

          .append(value(e.getCustomerGrade())).append("', '")
          .append(value(e.getBusinessArea())).append("', '")
          .append(value(e.getSn())).append("', '")
          .append(value(e.getFullName())).append("', ")
          .append(e.getGroupId()).append(", '")
          .append(value(e.getGroupName())).append("', '")

          .append(value(e.getProvince())).append("', '")
          .append(value(e.getCity())).append("', '")
          .append(value(e.getCounty())).append("', '")
          .append(value(e.getCustomerSn())).append("', '")

          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(e.getCreateTime().format(dateTimeFormatter)).append("', '")
          .append(e.getUpdateTime().format(dateTimeFormatter)).append("', '")
          .append(value(e.getReceiverName())).append("', '")
          .append(value(e.getReceiverPhone())).append("', '")
          .append(value(e.getCoordinate())).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
