package com.robot.gs.ticket.http.jira.result;

import lombok.Data;

@Data
public class StatusStatisticDTOS {
    String statusId;
    //String statusName;
    String timeHistoryStr;
    Long startDate;
    Long endDate;
    Long duration;
    String timeSpentStr;
    Long timeSpentDuration;
}
