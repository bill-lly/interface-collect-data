package com.robot.gs.udesk.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class RobotType {
  private long id;
  //产品型号
  private String name;
  //备注
  private String note;
  //产品型号
  private String productSn;
  //产品线
  private long robotFamilyId;
  private String robotFamilyCode;
  //整机版本号
  private String machineVersion;

  private Map<String, FieldRecord> extraFields = new HashMap();
  private LocalDateTime createTime;
  private LocalDateTime updateTime;

}
