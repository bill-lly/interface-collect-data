package com.robot.gs.udesk.integration;

import com.robot.gs.common.DataProcessor;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.robot.gs.udesk.integration.Helper.value;

public class FaultCategoryProcessor extends DataProcessor {

  private final FaultCategoryConverter converter = new FaultCategoryConverter();
  private final static String dbName = "gs_ods";
  private final static String tbName = "ods_f_udesk_fault_category";

  public FaultCategoryProcessor(LocalDate startDate) {
    super(startDate);
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {
    DateTimeFormatter ptDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("SELECT INLINE (ARRAY ( ");
    for (Map<String, Object> data : dataList) {
      FaultCategory e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getStatus())).append("', '")
          .append(value(e.getTopCategory())).append("', '")
          .append(value(e.getSecondCategory())).append("', '")
          .append(value(e.getThirdCategory())).append("', '")
          .append(value(e.getFaultNo())).append("', '")
          .append(e.getCreateTime()).append("', '")
          .append(e.getUpdateTime()).append("', '")
          .append(value(e.getCreateUser())).append("', '")
          .append(value(e.getUpdateUser())).append("', '")
          .append(value(e.getOwner())).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
