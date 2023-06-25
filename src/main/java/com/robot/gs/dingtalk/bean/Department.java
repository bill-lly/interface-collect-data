package com.robot.gs.dingtalk.bean;

import lombok.Data;

@Data
public class Department {
    // 部门ID
    private int deptId;
    // 部门名称
    private String name;
    // 部门级别
    private int deptLevel;
    // 父部门ID
    private int parentId;
    // 当部门群已经创建后，是否有新人加入部门会自动加入该群：true自动加入群,false：不会自动加入群
    private boolean autoAddUser;
    // 是否同步创建一个关联此部门的企业群：true：创建,false：不创建
    private boolean CreateDeptGroup;
    // example: "{\"faceCount\":\"13\"}",13为总人数
    private String ext ;
}
