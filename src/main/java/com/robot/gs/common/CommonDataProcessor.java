package com.robot.gs.common;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
@Slf4j
public abstract class CommonDataProcessor extends DataProcessor {
  protected ApiNameEnum apiNameEnum;
  protected String dbName;
  protected String tbName;
  protected LocalDate startDate;
  protected boolean hasPartition;
  protected CommonDataConverter converter;

  public CommonDataProcessor(ApiNameEnum apiNameEnum,
                             String dbName, String tbName,
                             LocalDate startDate, boolean hasPartition) {
    super(startDate);
    this.apiNameEnum = apiNameEnum;
    this.dbName = dbName;
    this.tbName = tbName;
    this.startDate = startDate;
    this.hasPartition = hasPartition;
    this.converter = createConverter(apiNameEnum);
  }

  protected abstract CommonDataConverter createConverter(ApiNameEnum apiNameEnum);

  protected Object getFieldValue(Field field, Object instance) {
    String name = field.getName();
    Class type = field.getType();
    try {
      Method m = instance.getClass().getDeclaredMethod(String.format("get%s%s", name.substring(0,1).toUpperCase(), name.substring(1)));
      return DataUtil.entity2SqlValue(type, m.invoke(instance));
    } catch (Exception e) {
      log.error(String.format("fail to get filed value, class=%s, field=%s", instance.getClass().getName(), name), e);
      throw new RuntimeException(e);
    }
  }
}
