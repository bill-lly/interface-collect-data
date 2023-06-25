package com.robot.gs.udesk.integration;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.common.CommonDataProcessor;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class UdeskDataProcessor extends CommonDataProcessor {

  public UdeskDataProcessor(ApiNameEnum apiNameEnum,
                            String dbName, String tbName,
                            LocalDate startDate, boolean hasPartition) {
    super(apiNameEnum, dbName, tbName, startDate, hasPartition);
  }

  @Override
  protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
    switch (apiNameEnum) {
      case WORK_HOURS_USAGE_RECORD:
        return new CommonDataConverter(WorkHoursUsageRecord.class);
      case SERVICE_ITEM_USAGE_RECORD:
        return new CommonDataConverter(ServiceItemUsageRecord.class);
      case SERVICE_ITEM_MANAGEMENT:
        return new CommonDataConverter(ServiceItemManagement.class);
      case TECHNICAL_TRANS_PROJECT_MANAGEMENT:
        return new CommonDataConverter(TechTransProjectManagement.class);
      case REPLACEMENT_PART_USE:
        return new CommonDataConverter(ReplacementPartUse.class);
      case CARGO_MANAGEMENT:
        return new CommonDataConverter(CargoMangement.class);
      default:
        throw new RuntimeException("no matching converter");
    }
  }

  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {
    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("PARTITION (pt)\n")
        .append("SELECT INLINE (ARRAY ( ");
    boolean doFinish = false;
    for (Map<String, Object> data : dataList) {
      Object e = from(data);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      builder.append("STRUCT ('");
      Field[] fields = e.getClass().getDeclaredFields();
      for (int i=0; i<fields.length; i++) {
        Object v = getFieldValue(fields[i], e);
        //修复字段value含有'导致的stmt解析sql失败
        if (v != null) {
          if (v.toString().contains("'")) {
            String[] split = v.toString().split("'");
            for (String s : split) {
              builder.append(s)
                      .append("\\")
                      .append("'");
            }
            builder.substring(0, builder.length() - 2);
          } else {
            builder.append(v);
          }
        }
        if (i == fields.length-1) {
          if(hasPartition == true) {
            builder.append("','" + formatter.format(startDate));
          }
          builder.append("'), ");
        } else {
          builder.append("', '");
        }
      }
    }
    if (builder.toString().endsWith(", ")) {
      builder.delete(builder.length() - 2, builder.length())
          .append("))");
      stmt.execute(builder.toString());
    }
  }

  private Object from(Map<String, Object> data) {
    Map<String, Object> idMap = new HashMap<>();
    idMap.put("fieldApiName", "id");
    idMap.put("fieldValue", Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    fieldList.add(idMap);
    return converter.converter(new Function<String, Object>() {
      @Override
      public Object apply(String s) {
        Iterator<Map<String, Object>> iterator = fieldList.iterator();
        while (iterator.hasNext()) {
          Map<String, Object> map = iterator.next();
          String fieldApiName = map.get("fieldApiName").toString();
          if (s.endsWith("_fieldValue") || s.endsWith("_foreignDataName") || s.endsWith("_userEmail") || s.endsWith("_ownerResult")) {
            String[] splitStr = s.split("_");
            if (splitStr[0].equalsIgnoreCase(fieldApiName) && splitStr[1].equalsIgnoreCase("fieldValue")) {
              return map.get("fieldValue");
            }
            if (splitStr[0].equalsIgnoreCase(fieldApiName) && splitStr[1].equalsIgnoreCase("foreignDataName")) {
              iterator.remove();
              return map.get("foreignDataName");
            }
            if (splitStr[0].equalsIgnoreCase(fieldApiName) && splitStr[1].equalsIgnoreCase("userEmail")) {
              iterator.remove();
              return map.get("userEmail");
            }
            if (splitStr[0].equalsIgnoreCase(fieldApiName) && splitStr[1].equalsIgnoreCase("ownerResult")) {
              iterator.remove();
              Map<String, Object> ownerResult = (Map<String, Object>)map.get("ownerResult");
              return ownerResult.get("ownerName");
            }
          } else {
            if (s.equalsIgnoreCase(map.get("fieldApiName").toString())) {
              iterator.remove();
              return map.get("fieldValue");
            }
          }
        }
        return null;
      }
    });
  }


}
