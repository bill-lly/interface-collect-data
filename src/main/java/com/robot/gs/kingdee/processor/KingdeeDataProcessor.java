package com.robot.gs.kingdee.processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class KingdeeDataProcessor {
    protected final LocalDateTime startDateTime;
    protected final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    protected final Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .setDateFormat(DateFormat.LONG)
            .create();
    private int count = 0;

    public KingdeeDataProcessor(LocalDate startDate) {
        if (null == startDate) {
            startDate = LocalDate.now().minusDays(1);
        }
        this.startDateTime = LocalDateTime.parse(startDate + " 00:00:00", dateTimeFormatter);
    }

    public KingdeeDataProcessor(String startDate) {
        if (null == startDate) {
            startDate = LocalDate.now().minusDays(1).toString();
        }
        this.startDateTime = LocalDateTime.parse(startDate + " 00:00:00", dateTimeFormatter);
    }

    public KingdeeDataProcessor() {
        this(LocalDate.now().minusDays(1));
    }

    public abstract void save(Statement stmt, List<String> dataList,
                              Boolean isFirst) throws SQLException;

    public int getCount() {
        return count;
    }

    public void increase(int i) {
        count = count + i;
    }
}
