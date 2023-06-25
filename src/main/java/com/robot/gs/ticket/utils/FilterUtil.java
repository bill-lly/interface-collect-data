package com.robot.gs.ticket.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public final class FilterUtil {
  private FilterUtil(){

  }

  /**
   * filter转map方法.
   *
   * @param filter filter转map
   * @return
   */
  public static Map<String, String> toMap(final String filter) {
    Map<String, String> map = new HashMap<>();
    if (StringUtils.isNotEmpty(filter)) {
      String[] params = filter.split(",");
      for (int i = 0; i < params.length; i++) {
        String param = params[i];
        String[] mapString = param.trim().split("==");
        map.put(mapString[0].trim(), mapString[1].trim());
      }
    }
    return map;
  }
}
