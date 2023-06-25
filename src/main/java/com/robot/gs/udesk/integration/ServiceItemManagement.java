package com.robot.gs.udesk.integration;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServiceItemManagement {
    private Long id;
    //服务项目
    private String name;
    //产品线
    private String chanpinxian_fieldValue;
    //产品线
    private String chanpinxian_foreignDataName;
    //服务时长
    private Double fuwushichang;
    //服务站类型
    private String fuwushangleixing;
    //服务类型
    private String fuwuleixing;
    //费用标准
    private Double feiyongbiaozhun;
}
