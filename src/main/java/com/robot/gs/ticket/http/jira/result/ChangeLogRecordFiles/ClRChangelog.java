package com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles;

import lombok.Data;

import java.util.List;

@Data
public class ClRChangelog {
    List<ClRChangelogHistories> histories;
}
