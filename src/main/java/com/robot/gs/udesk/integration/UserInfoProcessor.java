package com.robot.gs.udesk.integration;

import com.robot.gs.common.DataProcessor;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import static com.robot.gs.udesk.integration.Helper.value;

public class UserInfoProcessor extends DataProcessor {
  private final UserInfoConverter converter = new UserInfoConverter();
  private final static String dbName = "gs_ods";
  private final static String tbName = "ods_f_udesk_user_info";

  public UserInfoProcessor(LocalDate startDate) {
    super(startDate);
  }

  public UserInfoProcessor() {
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {
    //获取分区日期
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String pt = formatter.format(startDateTime);

    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("SELECT INLINE (ARRAY ( ");
    for (Map<String, Object> data : dataList) {
      UserInfo e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getEmployeeNo())).append("', '")
          .append(value(e.getName())).append("', '")
          .append(value(e.getDepartment())).append("', '")
          .append(value(e.getEmployeeId())).append("', '")
          .append(value(e.getPhone())).append("', '")
          .append(value(e.getEmail())).append("', '")
          .append(value(e.getRegion())).append("', '")
          .append(value(e.getServiceStation())).append("', '")
          .append(value(e.getWarehouse())).append("', '")
          .append(value(e.getJobStatus())).append("', '")
          .append(value(e.getUserStatus())).append("', '")
          .append(value(e.getUserRoleIds())).append("', '")
          .append(e.getEntryDate()).append("', '")
          .append(e.getEntryDuration()).append("', '")
          .append(value(e.getCreateUser())).append("', '")
          .append(value(e.getUpdateUser())).append("', '")
          .append(e.getCreateTime()).append("', '")
          .append(e.getUpdateTime()).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(value(e.getWorkerStatus())).append("', '")
          .append(value(e.getWorkerProperty())).append("', '")
          .append(value(e.getWareHouseName())).append("', '")
          .append(pt).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
