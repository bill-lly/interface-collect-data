package com.robot.gs.common;

public enum ModuleEnum {
  UDESK("udesk"),
  WRITEUDESK("writeudesk"),
  JIRA("jira"),
  DINGTALK("dingtalk"),
  MES("mes"),
  KINGDEE("kingdee");

  private String name;

  ModuleEnum(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
