package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class UserInfo {
  private Long id;
  //员工编号
  private String employeeNo;
  //员工姓名
  private String name;
  //部门
  private String department;
  //员工工号
  private String employeeId;
  //电话
  private String phone;
  //登录名
  private String email;
  //所属区域
  private String region;
  //所属服务站
  private String serviceStation;
  //仓库
  private String warehouse;
  //状态
  private String jobStatus;
  //关联员工状态
  private String userStatus;
  //关联员工角色
  private String userRoleIds;
  //入职时间
  private LocalDate entryDate;
  //入职时长
  private Integer entryDuration;
  //创建人
  private String createUser;
  //修改人
  private String updateUser;
  //创建时间
  private LocalDateTime createTime;
  //更新时间
  private LocalDateTime updateTime;
  //在职状态（导出用）
  private String workerStatus;
  //人员属性
  private String workerProperty;
  //仓库名称
  private String wareHouseName;

  private Map<String, FieldRecord> extraFields = new HashMap();
}
