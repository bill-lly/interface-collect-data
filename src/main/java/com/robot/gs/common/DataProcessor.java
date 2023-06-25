package com.robot.gs.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class DataProcessor {
  protected final LocalDateTime startDateTime;
  protected final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected final Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
      .setDateFormat(DateFormat.LONG)
      .create();
  private int count = 0;

  public DataProcessor(LocalDate startDate) {
    if (null == startDate) {
      startDate = LocalDate.now().minusDays(1);
    }
    this.startDateTime = LocalDateTime.parse(startDate + " 00:00:00", dateTimeFormatter);
  }

  public DataProcessor(String startDate) {
    if (null == startDate) {
      startDate = LocalDate.now().minusDays(1).toString();
    }
    this.startDateTime = LocalDateTime.parse(startDate + " 00:00:00", dateTimeFormatter);
  }

  public DataProcessor() {
    this(LocalDate.now().minusDays(1));
  }

  public abstract void save(Statement stmt, List<Map<String, Object>> dataList,
      Boolean isFirst) throws SQLException;

  public int getCount() {
    return count;
  }

  public void increase(int i) {
    count = count + i;
  }
}
