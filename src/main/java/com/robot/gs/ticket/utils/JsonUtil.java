package com.robot.gs.ticket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {

  private JsonUtil() {

  }

  /**
   * 序列化对象方法.
   *
   * @param obj 序列化入参
   * @return
   */
  public static String serialize(Object obj) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      log.error("jackson serialize is error ", e);
    }
    return null;
  }

  /**
   * 反序列化方法.
   *
   * @param resp 输入jsonStr
   * @param clz 反序列化目标类
   * @param <T> 反序列化对象类型
   * @return
   */
  public static <T> T deSerialize(String resp, Class<T> clz) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(resp, clz);
    } catch (JsonProcessingException e) {
      log.error("jackson deselizate is error ", e);
      throw new RuntimeException(e);
    }
  }
}
