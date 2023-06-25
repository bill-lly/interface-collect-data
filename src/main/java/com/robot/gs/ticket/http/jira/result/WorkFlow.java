package com.robot.gs.ticket.http.jira.result;

import lombok.Data;
import org.datanucleus.store.rdbms.query.AbstractRDBMSQueryResult;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorkFlow {
    String issueKey;
    String assigneeId;
    String userName;
    //String assigneeFullName;
    List<StatusStatisticDTOS> statusStatisticDTOS;
    long startDate;
    long endDate;
    String timeHistoryStr;
    String timeSpentStr;
}
