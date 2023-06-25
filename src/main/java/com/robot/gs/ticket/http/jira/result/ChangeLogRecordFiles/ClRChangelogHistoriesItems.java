package com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles;

import lombok.Data;

@Data
public class ClRChangelogHistoriesItems {
    String field;
    String fieldtype;
    String from;
    String fromString;
    String to;
    String toString;
}
