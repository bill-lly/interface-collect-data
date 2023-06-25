package com.robot.gs.ticket.http.udesk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "udesk")
public class ServiceGoConfig {
  private String userId;
  private String apiToken;
  private String email;
  private String apiUrlPrefix;
  private Boolean isMock;
  private Integer filterId;
}
