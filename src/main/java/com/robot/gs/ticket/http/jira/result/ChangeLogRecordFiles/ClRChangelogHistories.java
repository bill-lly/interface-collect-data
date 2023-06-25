package com.robot.gs.ticket.http.jira.result.ChangeLogRecordFiles;

import lombok.Data;

import java.util.List;

@Data
public class ClRChangelogHistories {
    String id;
    ClRChangelogHistoriesAuthor author;
    String created;
    List<ClRChangelogHistoriesItems> items;
}
