package com.robot.gs.ticket.http.kingdee.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Data
@ConfigurationProperties(prefix = "kingdee")
public class ServiceGoConfig {
    private String scheme;
    private String host;
    private String pathSegment1;
    private String pathSegment2;
    private String sysId;
    private String viewDetailServId;
    private String noListServId;
    private UUID serialNo;
    private String sessionIdServId;
    private String sessionIdAcctid;
    private String sessionIdUserName;
    private String sessionIdPassword;
    private int sessionIdLcid;
}
