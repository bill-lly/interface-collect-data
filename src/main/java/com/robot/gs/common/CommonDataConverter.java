package com.robot.gs.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Function;

@Slf4j
public class CommonDataConverter<T> {

  private Class<T> clazz;

  public CommonDataConverter(Class<T> clazz) {
    this.clazz = clazz;
  }

  public T converter(Function<String, Object> function) {
    try {
      Field[] fields = clazz.getDeclaredFields();
      T instance = clazz.newInstance();
      for (Field f:fields) {
        String name = f.getName();
        Class type = f.getType();
        Object o = function.apply(name);
        Method m = clazz.getDeclaredMethod(String.format("set%s%s", name.substring(0,1).toUpperCase(), name.substring(1)), type);
        m.invoke(instance, DataUtil.str2EntityValue(type, o));
      }
      return instance;
    } catch (Exception e) {
      log.error(String.format("failed to converter to %s", clazz.getName()), e);
      throw new RuntimeException(e);
    }
  }


}
