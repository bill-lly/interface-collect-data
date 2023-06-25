package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class KingdeeDataConverter<T> {

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

    //获取json套json套json_list中字段
    protected String getListValue(String fieldOut, String fieldIn, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        if (jsonObject.get(fieldOut) != null) {
            JSONObject obj = jsonObject.getJSONObject(fieldOut);
            List<JSONObject> resultList = obj.getList(fieldIn, JSONObject.class);
            if (resultList.size() != 0) {
                for (JSONObject result : resultList) {
                    if (result.getInteger("Key") == 2052) {
                        return result.get("Value").toString();
                    }
                }
                return resultList.get(0).get("Value").toString();
            }
        }
        return null;
    }

    //获取json套json_list中字段
    protected String getSimListValue(String fieldIn, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        List<JSONObject> resultList = jsonObject.getList(fieldIn, JSONObject.class);
        if (resultList.size() != 0) {
            for (JSONObject result : resultList) {
                if (result.getInteger("Key") == 2052) {
                    return result.get("Value").toString();
                }
            }
            return resultList.get(0).get("Value").toString();
        }
        return null;
    }

    //获取json套json中字段
    protected String getJsonValue(String fieldOut, String fieldIn, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        JSONObject obj = (JSONObject) jsonObject.get(fieldOut);
        return obj != null ? obj.getString(fieldIn) : null;
    }
}
