package com.robot.gs.udesk.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * https://servicego.udesk.cn/site/admin/custom-object/8814/fields
 */
@Data
public class TerminalUser {
  long id;
  //终端客户
  String name;
  //客户地址
  String address;
  //客户等级
  private String customerGrade;
  //所属大区
  String businessArea;
  //客户编号
  String sn;
  //公司全名
  String fullName;
  //客户组
  long groupId;
  String groupName;
  //关联客户
  //String associatedCustomer;

  //省
  private String province;
  //市
  private String city;
  //区/县
  private String county;
  //客户编号
  private String customerSn;

  LocalDateTime createTime;
  LocalDateTime updateTime;
  //收件人
  String receiverName;
  //收件人电话
  String receiverPhone;
  // 坐标
  private String coordinate;
  private Map<String, FieldRecord> extraFields = new HashMap();
}
