package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorCode {
    private Long id;
    // Error code
    private Long errorCode;
    // Code名称
    private String errorName;
    // 健康等级
    private String healthLevel;
    // 运维等级
    private String opLevel;
    // 适用机型
    private String applicableModel;
    // 备注
    private String remarks;
    // 创建时间
    LocalDateTime createTime;
    // 更新时间
    LocalDateTime updateTime;
    // 创建人
    private Long createUserId;
    private String createUserEmail;
    // 修改人
    private Long updateUserId;
    private String updateUserEmail;
    // 所有人
    private Long ownerId;
    private String ownerResult;
}
