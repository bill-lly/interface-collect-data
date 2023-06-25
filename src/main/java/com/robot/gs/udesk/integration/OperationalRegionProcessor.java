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
public class OperationalRegionProcessor extends DataProcessor {
  private final OperationalRegionConverter converter = new OperationalRegionConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_operational_region";

  public OperationalRegionProcessor(LocalDate startDate) {
    super(startDate);
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst)
      throws SQLException {
    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("SELECT INLINE (ARRAY ( ");
    DateTimeFormatter ptDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    for (Map<String, Object> data : dataList) {
      OperationalRegion e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getBusinessArea())).append("', '")
          .append(value(e.getServiceStationType())).append("', '")
          .append(e.getCountry()).append("', '")
          .append(value(e.getProvince())).append("', '")
          .append(value(e.getCity())).append("', '")
          .append(value(e.getCounty())).append("', '")
          .append(value(e.getServiceStationLevel())).append("', '")
          .append(value(e.getSn())).append("', ")
          .append(e.getSupervisorId()).append(", '")
          .append(value(e.getSupervisorEmail())).append("', '")
          .append(value(e.getManHourCity())).append("', '")
          .append(value(e.getManHourCountry())).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
