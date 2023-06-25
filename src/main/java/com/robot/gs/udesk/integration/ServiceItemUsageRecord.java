package com.robot.gs.udesk.integration;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceItemUsageRecord {
    private Long id;
    //记录编号
    private String name;
    //所属工单字段值
    private String suoshugongdan_fieldValue;
    //所属工单外键关联值
    private String suoshugongdan_foreignDataName;
    //服务项目字段值
    private String fuwuxiangmu_fieldValue;
    //服务项目外键关联值
    private String fuwuxiangmu_foreignDataName;
    //费用标准
    private Double feiyongbiaozhun;
    //所属大区
    private String suoshudaqu;
    //所属服务站字段值
    private String suoshufuwuzhan_fieldValue;
    //所属服务站外键关联值
    private String suoshufuwuzhan_foreignDataName;
    //服务类别
    private String fuwuleibie;
    //服务站类型
    private String fuwuzhanleixing;
    //产品线字段值
    private String chanpinxian_fieldValue;
    //产品线外键关联值
    private String chanpinxian_foreignDataName;
    //创建时间
    private LocalDateTime createTime;
    //创建人
    private String createUser;
    //更新时间
    private LocalDateTime updateTime;
    //修改人
    private String updateUser;
}
