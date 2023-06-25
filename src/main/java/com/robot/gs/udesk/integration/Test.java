package com.robot.gs.udesk.integration;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

import com.robot.gs.common.ApiNameEnum;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;

@Slf4j
public class Test {
  public static void main(String[] args) {
    log.info(LocalDate.now().toString());
    try (HiveDataSource ds = new HiveDataSource("/hive.properties");
        Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement()) {
      Properties properties = loadProperties("/udesk.properties");

//      UdeskClient udeskClient = new UdeskClient(properties, ApiNameEnum.TERMINAL_USER_GROUP,
//          "terminal.user.group.filter.id", new TerminalUserGroupProcessor());
//      udeskClient.getAndSaveData(stmt, null);
    } catch (Exception e) {
      log.error("Can not download and save udesk data", e);
    }

  }
}
