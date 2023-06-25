package com.robot.gs.udesk.integration;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class OperationalRegion {
  int id;
  //服务站名称
  String name;
  //所属大区
  String businessArea;
  //服务站类型
  String serviceStationType;

  private String country;
  //省
  private String province;
  //市
  private String city;
  //区/县
  private String county;
  //服务站级别
  private String serviceStationLevel;
  //服务站编号
  private String sn;
  //负责人
  private int supervisorId;
  private String supervisorEmail;

  //适用工时城市
  private String manHourCity;
  //适用工时国家
  private String manHourCountry;

  private Map<String, FieldRecord> extraFields = new HashMap();
}
