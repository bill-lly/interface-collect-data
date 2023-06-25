package com.robot.gs.ticket.http.udesk.enums.station;

public enum  ServiceFieldDataEnum {
  NAME("name","服务站名称",""),
  OWNER("fuzeren", "负责人", ""),
  REGION("belongsArea","大区",""),
  EMAIL("userEmail", "负责人邮箱", "");

  ServiceFieldDataEnum(String key, String description, String defaultValue) {
    this.key = key;
    this.description = description;
    this.defaultValue = defaultValue;

  }

  private String key;
  private String description;
  private String defaultValue;

  public String getKey() {
    return key;
  }

  public String getDescription() {
    return description;
  }

  public String getDefaultValue() {
    return defaultValue;
  }
}
