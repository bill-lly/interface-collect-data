package com.robot.gs.udesk.integration;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class Customer {
  long id;
  //客户名称
  String name;
  //客户编号
  String sn;
  //客户重要性
  String grade;

  //国家
  private String country;
  //省
  private String province;
  //市
  private String city;
  //区/县
  private String county;
  //详细地址
  private String address;
  //坐标
  private String coordinate;
  //备注
  private String note;
  //客户类型
  private String customerType;
  //是否有效客户
  private String isEfficientCustomer;
  //大区
  private String businessArea;
  //客户性质
  private String customerNature;
  //客户行业
  private String customerIndustry;
  //客户身份
  private String customerIdentity;

  private Map<String, FieldRecord> extraFields = new HashMap();

}
