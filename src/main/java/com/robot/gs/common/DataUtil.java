package com.robot.gs.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataUtil {

  public static Object str2EntityValue(Class clazz, Object o) {
    if (clazz.getName().equals("java.lang.String")) {
      return o == null ? "" : o.toString();
    } else if (clazz.getName().equals("java.lang.Integer")) {
      return o == null ? 0 : Integer.parseInt(o.toString());
    } else if (clazz.getName().equals("java.lang.Long")) {
      return o == null ? 0L : Long.parseLong(o.toString());
    } else if (clazz.getName().equals("java.lang.Double")) {
      return o == null ? new Double(0) : Double.parseDouble(o.toString());
    } else if (clazz.getName().equals("java.time.LocalDateTime")) {
      return o == null ? null : LocalDateTime.parse(o.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    } else if (clazz.getName().equals("java.time.LocalDate")) {
      return o == null ? null : LocalDate.parse(o.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    return null;
  }

  public static Object entity2SqlValue(Class clazz, Object o) {
    if (clazz.getName().equals("java.lang.String")) {
      return o == null ? "" : o;
    } else if (clazz.getName().equals("java.lang.Integer")) {
      return o == null ? 0 : o;
    } else if (clazz.getName().equals("java.lang.Long")) {
      return o == null ? 0L : o;
    } else if (clazz.getName().equals("java.lang.Double")) {
      return o == null ? new Double(0) : o;
    } else if (clazz.getName().equals("java.time.LocalDateTime")) {
      return o == null ? null : ((LocalDateTime)o).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    } else if (clazz.getName().equals("java.time.LocalDate")) {
      return o == null ? null : ((LocalDate)o).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    } else if (clazz.getName().equals("java.util.ArrayList")) {
      return o == null ? new ArrayList() : o;
    }
    return null;
  }

  /**
   * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
   * 2020-04-09T23:00:00.000+08:00 TO 2020-04-09 23:00:00
   * @throws ParseException
   */
  public static String dealDateFormat(String oldDateStr) throws ParseException {
    if(oldDateStr.equals("")) {
      return  "";
    }
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    Date date = df.parse(oldDateStr);
    SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
    Date date1 =  df1.parse(date.toString());
    DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return df2.format(date1);
  }

}
