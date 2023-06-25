package com.robot.gs.common;

import com.robot.gs.udesk.integration.Helper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class JDBCDataSource implements AutoCloseable {
  private static String driverName ;
  private final String url;
  private final String user;
  private final String password;

  public JDBCDataSource(String configFile) throws IOException {
    Properties properties = Helper.loadProperties(configFile);
    url = properties.getProperty("url");
    user = properties.getProperty("user");
    password = properties.getProperty("password");
    driverName = properties.getProperty("driverName");
  }

  public JDBCDataSource(String url, String user, String password) {
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
