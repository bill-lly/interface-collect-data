package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkHoursUsageRecord {
  private Long id;
  //记录编号
  private String name;
  //所属工单
  private String suoshugongdan_fieldValue;
  private String suoshugongdan_foreignDataName;
  //标准名称
  private String biaozhunmingchen_fieldValue;
  private String biaozhunmingchen_foreignDataName;
  //工时
  private Double gongshi;
  //工时标准
  private Double gongshibiaozhun;
  //工时费用
  private Double gongshifeiyong;
  //所属服务站
  private String suoshufuwuzhan_fieldValue;
  private String suoshufuwuzhan_foreignDataName;
  //适用国家
  private String shiyongguojia;
  //适用城市
  private String shiyongchengshi;
  //服务类型
  private String fuwuleixing;
  //服务站类别
  private String fuwuzhanleibie;
  //所属大区
  private String suoshudaqu;
  //创建时间
  private LocalDateTime createTime;
  //创建人
  private String createUser;
  //更新时间
  private LocalDateTime updateTime;
  //修改人
  private String updateUser;

}
