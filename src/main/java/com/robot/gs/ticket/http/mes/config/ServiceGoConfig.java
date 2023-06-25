package com.robot.gs.ticket.http.mes.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "mes")
public class ServiceGoConfig {
    private String url;
    private String api_id;
    private String api_key;
    private String sysId;
    // 获取车号服务号
    private String servIdModelNumber;
    // 根据车号获取入库时间服务
    private String servIdByModelNumber;
    // 根据时间获取车号入库时间
    private String servIdByDate;
}
