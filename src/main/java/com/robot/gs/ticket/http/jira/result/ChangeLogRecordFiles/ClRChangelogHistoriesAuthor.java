package com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles;

import lombok.Data;

@Data
public class ClRChangelogHistoriesAuthor {
    String name;
    String key;
    String emailAddress;
    String displayName;
    String active;
    String timeZone;
}
