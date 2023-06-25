package com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles;

import lombok.Data;

@Data
public class ChangelogRecord {
    String id;
    String key;
    ClRChangelog changelog;
}
