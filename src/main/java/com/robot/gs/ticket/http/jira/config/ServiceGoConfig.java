package com.robot.gs.ticket.http.jira.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "jira")
public class ServiceGoConfig {
  private String apiUrlPrefix;
  private String apiUrlWorkflow;
  private String apiUrlWorkflowStatus;
  private String apiUrlChangelogRecord;
  private String userName;
  private String passWord;
}
