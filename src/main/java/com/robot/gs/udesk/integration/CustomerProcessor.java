package com.robot.gs.udesk.integration;

import static com.robot.gs.udesk.integration.Helper.value;
import com.robot.gs.common.DataProcessor;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerProcessor extends DataProcessor {
  private final CustomerConverter converter = new CustomerConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_customer";

  public CustomerProcessor(LocalDate startDate) {
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
      Customer e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getSn())).append("', '")
          .append(value(e.getNote())).append("', '")
          .append(value(e.getGrade())).append("', '")
          .append(value(e.getCountry())).append("', '")
          .append(value(e.getProvince())).append("', '")
          .append(value(e.getCity())).append("', '")
          .append(value(e.getCounty())).append("', '")
          .append(value(e.getAddress())).append("', '")

          .append(value(e.getCoordinate())).append("', '")
          .append(value(e.getCustomerType())).append("', '")
          .append(value(e.getIsEfficientCustomer())).append("', '")
          .append(value(e.getBusinessArea())).append("', '")
          .append(value(e.getCustomerNature())).append("', '")
          .append(value(e.getCustomerIndustry())).append("', '")
          .append(value(e.getCustomerIdentity())).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
