package com.robot.gs.jira.integration;

import lombok.Data;

@Data
public class ChangelogRecordOpen {
    String id;
    String key;
    String historyId;
    String historyAuthorName;
    String historyAuthorKey;
    String historyAuthorEmailAddress;
    String historyAuthorDisplayName;
    String historyAuthorActive;
    String historyAuthorTimeZone;
    String historyCreated;
    String historyItemsField;
    String historyItemsFieldtype;
    String historyItemsFrom;
    String historyItemsFromString;
    String historyItemsTo;
    String historyItemsToString;
}
