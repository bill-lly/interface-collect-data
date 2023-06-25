package com.robot.gs.ticket.http.udesk.enums.order;

public enum WorkOrderFieldDataEnum {

  DESCRIPTION("description", "故障描述", ""),
  TYPE("gongdanleibie", "工单类别", "维修"),
  SUBJECT("subject", "工单描述", "App手动创建"),
  STATE("gongdanzhuangtai", "工单状态", StateEnum.UNASSIGNED.getDescription()),
  ASSIGNEE("ownerEngine","负责FAE","wuyadong@gs-robot.com"),
  CONTACT("kehuzhuyaolianxiren","联系人",""),
  CONTACT_PHONE("zhuyaolianxirendianhua","联系人电话",""),
  ENGINEER_PHONE("gongchengshidianhua","工程师电话","13917518929"),
  COMMENT("paigongbeizhu","派工备注",""),
  APPENDIX("shangchuanfujian","上传附件",""),
  PHOTO("zhaopianshipin","故障照片",""),
  REGION("suoshudaqu","所属大区",""),
  CREATE_USER("createUser","创建人","1@gs-robot.com"),
  DATA_SOURCE("shujulaiyuan","数据来源","掌上高仙客户"),
  TICKET_ID("sysUTicketKeyId","Udesk工单ID",""),
  BUSSINESS_NO("gongdanbianhao","工单编号",""),
  SN("shebeiSNhao", "设备S/N号", ""),
  UPDATE_TIME("updateTime", "更新时间", "");


  WorkOrderFieldDataEnum(String key, String description, String defaultValue) {
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
