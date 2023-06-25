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
public class TerminalUserGroupProcessor extends DataProcessor {
  TerminalUserGroupConverter converter = new TerminalUserGroupConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_terminal_user_group";

  public TerminalUserGroupProcessor(LocalDate startDate) {
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
      TerminalUserGroup e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getSn())).append("', '")
          .append(value(e.getCustomerGrade())).append("', '")
          .append(value(e.getBargainMode())).append("', '")
          .append(value(e.getTerminalIndustry())).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(value(e.getKeyAccountManager())).append("', '")
          .append(value(e.getHeadOfSales())).append("', '")
          .append(value(e.getCountry())).append("', '")
          .append(value(e.getGroupCompanyName())).append("', '")
          .append(value(e.getCustomerCategory())).append("', '")
          .append(value(e.getIndustryAttribute())).append("', '")
          .append(value(e.getCreateTime().format(dateTimeFormatter))).append("', '")
          .append(value(e.getUpdateTime().format(dateTimeFormatter))).append("', '")
          .append(e.getCreateUserId()).append("', '")
          .append(value(e.getCreateUserEmail())).append("', '")
          .append(startDateTime.format(ptDateFormatter)).append("'), ");
         ;
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
