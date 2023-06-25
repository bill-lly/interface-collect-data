package com.robot.gs.udesk.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * https://servicego.udesk.cn/site/admin/custom-object/8820/fields
 */

@Data
public class TerminalUserGroup {
  long id;
  // 终端客户组
  String name;
  // 客户组编号
  String sn;
  // 客户等级
  String customerGrade;
  // 成交方式
  String bargainMode;
  // 终端业态
  String terminalIndustry;
  // 大客户经理
  String keyAccountManager;
  // 销售负责人
  String headOfSales;
  // 国家
  String country;
  // 集团公司全称
  String groupCompanyName;
  // 客户类别
  String customerCategory;
  // 行业属性
  String industryAttribute;
  // 创建时间
  LocalDateTime createTime;
  // 更新时间
  LocalDateTime updateTime;
  //创建人
  Long createUserId;
  String createUserEmail;

  private Map<String, FieldRecord> extraFields = new HashMap();
}
