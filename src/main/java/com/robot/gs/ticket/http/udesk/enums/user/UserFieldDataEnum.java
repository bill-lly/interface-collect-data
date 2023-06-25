package com.robot.gs.ticket.http.udesk.enums.user;

public enum UserFieldDataEnum {
  NAME("sysusername","姓名","吴亚东"),
  EMAIL("userEmail", "登录用户名", "");

  UserFieldDataEnum(String key, String description, String defaultValue) {
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
