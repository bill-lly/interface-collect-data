package com.robot.gs.dingtalk.bean;
import lombok.Data;

import java.util.List;

@Data
public class Employee {
    // 用户的userId
    private String userId;
    // 姓名
    private String name;
    // 用户在当前开发者企业帐号范围内的唯一标识
    private String unionId;
    // 职位
    private String title;
    // 员工工号
    private String jobNumber;
    // 分机号
    private String telephone;
    // 员工邮箱
    private String email;
    // 企业邮箱
    private String orgEmail;
    // 办公地点
    private String workPlace;
    // 备注
    private String remark;
    // 拓展字段
    private String extension;
    // 是否leader
    private boolean leader;
    // 部门id
    private Long deptId;
    // 部门列表
    private List<Long> deptIdList;
    // 是否激活了钉钉
    private boolean active;
    // 是否为企业的管理员
    private boolean admin;
    // 头像地址
    private String avatar;
    // 是否为企业的老板
    private boolean boss;
    // 员工在部门中的排序
    private Long deptOrder;
    // 是否专属帐号
    private boolean exclusiveAccount;
    // 是否隐藏号码
    private boolean hideMobile;
    // 入职时间，Unix时间戳，单位毫秒
    private Long hiredDate;

}
