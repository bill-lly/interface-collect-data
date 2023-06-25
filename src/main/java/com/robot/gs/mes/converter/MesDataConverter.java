package com.robot.gs.mes.converter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class MesDataConverter<T> {

    public abstract T from(Map<String, Object> data);

    protected final DateTimeFormatter dateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    protected final Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .setDateFormat(DateFormat.LONG)
            .create();

    protected Integer toInt(Object v) {
        return toInt(v, 0);
    }

    private Integer toInt(Object v, int i) {
        return v == null ? i : Integer.parseInt(v.toString());
    }

    protected String value(Object v) {
        return value(v, "");
    }

    protected String value(Object v, String defaultValue) {
        return v == null ? defaultValue : v.toString();
    }

    protected Double value(Object v, Double defaultValue) {
        return v == null ? defaultValue : (Double) v;
    }
}
