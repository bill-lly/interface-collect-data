package com.robot.gs.common;

public enum ApiNameEnum {
  //udesk
  EQUIPMENT_ARCHIVES("udesk", "EquipmentArchives","设备档案"),
  WORK_TICKET("udesk","case","工单"),
  SERVICE_STATION("udesk","ServiceStationManagement","服务站管理"),
  USER_INFO("udesk","sysuserinfo","员工管理"),
  TERMINAL_USER("udesk","zhongduankehu","终端客户"),
  TERMINAL_USER_GROUP("udesk","zhongduankehuzu", "终端客户组"),
  ROBOT_TYPE("udesk","ProductManagement", "机型管理"),
  CUSTOMER("udesk","organization", "客户"),
  FAULT_CATEGORY("udesk","guzhangfenleiguanli", "故障分类管理"),
  WORK_HOURS_USAGE_RECORD("udesk","gongshijilu", "工时使用记录"),
  SERVICE_ITEM_USAGE_RECORD("udesk","fuwuxiangmushiyongjilu", "服务项目使用记录"),
  SERVICE_ITEM_MANAGEMENT("udesk","fuwu", "服务项目管理"),
  TECHNICAL_TRANS_PROJECT_MANAGEMENT("udesk","jigaixiangmuguanli", "技改项目管理"),
  Error_Code("udesk","ErrorCode", "Error code管理"),
  REPLACEMENT_PART_USE("udesk", "ReplacementPartUse", "备件使用单"),
  CARGO_MANAGEMENT("udesk", "CargoManagement", "配件清单"),

  //jira
  JIRA_SELLSERVIC("jira", "sellservic", "售前售后"),
  JIRA_SOFTREQ("jira", "softreq", "原始需求池"),
  JIRA_ZC("jira", "zc", "综合测试"),
  JIRA_QD("jira", "qd", "质量保证部"),


  DINGTALK("dingtalk","org","上海高仙dept&emp组织架构"),


  //Mes
  RrocessReport("mes","material_wh_kingdee","物料金蝶入库时间"),

  //KINGDEE
  KINGDEE_SAL_OUTSTOCK("kingdee", "SAL_OUTSTOCK", "销售出库单"),
  KINGDEE_SAL_RETURNSTOCK("kingdee", "SAL_RETURNSTOCK", "销售退货单"),
  KINGDEE_PUR_REQUISITION("kingdee", "PUR_Requisition", "采购申请单"),
  KINGDEE_PUR_PURCHASEORDER("kingdee", "PUR_PurchaseOrder", "采购订单"),
  KINGDEE_STK_INSTOCK("kingdee", "STK_InStock", "采购入库单"),
  KINGDEE_PUR_RECEIVEBILL("kingdee", "PUR_ReceiveBill", "收料通知单"),
  KINGDEE_BD_MATERIAL("kingdee", "BD_MATERIAL", "物料");

  private String module;
  private String name;
  private String description;

  ApiNameEnum(String module, String name, String description) {
    this.module = module;
    this.name = name;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getModule() {
    return module;
  }
}
