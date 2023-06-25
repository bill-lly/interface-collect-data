package com.robot.gs.ticket.http.udesk.enums.product;

public enum ProdcutFieldDataEnum {

  NAME("name", "产品线名称", "");

  ProdcutFieldDataEnum(String key, String description, String defaultValue) {
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
