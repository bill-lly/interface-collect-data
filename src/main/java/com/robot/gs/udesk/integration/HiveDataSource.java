package com.robot.gs.udesk.integration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HiveDataSource implements AutoCloseable {
  private static final String driverName = "org.apache.hive.jdbc.HiveDriver";
  private final String url;
  private final String user;
  private final String password;

  public HiveDataSource(String configFile) throws IOException {
    Properties properties = Helper.loadProperties(configFile);
    url = properties.getProperty("url");
    user = properties.getProperty("user");
    password = properties.getProperty("password");
  }

  public HiveDataSource(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
  }

  public Connection getConnection() throws SQLException {
    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      log.error("Can not find class " + driverName, e);
      throw new SQLException(e);
    }
    return DriverManager.getConnection(url, user, password);
  }

  @Override
  public void close() throws Exception {

  }
}
