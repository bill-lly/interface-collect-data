package com.robot.gs.jira.integration;

import lombok.Data;

@Data
public class WorkFlowOpen {
    String issueKey;
    String assigneeId;
    String userName;
    String statusId;
    String statusName;
    String timeHistoryStr;
    Long startDate;
    Long endDate;
    Long duration;
    String timeSpentStr;
    Long timeSpentDuration;
}
