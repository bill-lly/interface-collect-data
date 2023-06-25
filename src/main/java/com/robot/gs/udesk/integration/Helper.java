package com.robot.gs.udesk.integration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Helper {
  private static final Pattern QUOTE_PATTERN = Pattern.compile("\\'");

  public static Properties loadProperties(String fileName) throws IOException {
    Properties properties = new Properties();
    InputStream inputStream = Helper.class.getResourceAsStream(fileName);
    if (null == inputStream) {
      log.error(fileName + " does not exist");
      throw new FileNotFoundException(fileName + " does not exist");
    }
    properties.load(inputStream);
    return properties;
  }

  public static int getInt(Properties properties, String key, int defaultValue) {
    String value = properties.getProperty(key);
    return null == value ? defaultValue : Integer.parseInt(value);
  }

  public static String value(String raw) {
    return null == raw ? "" : QUOTE_PATTERN.matcher(raw).replaceAll("\\\\'");
  }

  public static String dateFormatter(LocalDateTime dateTime, DateTimeFormatter dateTimeFormatter) {
    if (dateTime == null) {
      return null;
    }
    return dateTime.format(dateTimeFormatter);
  }

  public static Integer intValue(Integer value) {
    return value == null ? 0 : value;
  }

}
