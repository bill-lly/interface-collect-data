package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class FaultCategory {
  private Long id;
  //故障分类名
  private String name;
  //状态
  private String status;
  //一级分类
  private String topCategory;
  //二级分类
  private String secondCategory;
  //三级分类
  private String thirdCategory;
  //故障编号
  private String faultNo;
  //创建时间
  private LocalDateTime createTime;
  //更新时间
  private LocalDateTime updateTime;
  //创建人
  private String createUser;
  //修改人
  private String updateUser;
  //所有人
  private String owner;

  private Map<String, FieldRecord> extraFields = new HashMap();

}
