package com.robot.gs.jira.integration;

import com.atlassian.jira.rest.client.api.domain.IssueField;
import lombok.Data;
import org.apache.hadoop.hbase.shaded.com.google.gson.internal.LinkedHashTreeMap;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;
import org.eclipse.jetty.util.ajax.JSON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
public class JiraInfo {
    private Long id;
    private String key;
    private String issueType;
    private String labels;
    private String status;
    private String projectKey;
    private String projectName;
    //private String transitionsUri;
    //private String expandos;
    private String components;
    private String summary;
    private String description;
    private String reporter;
    private String assignee;
    private String resolution;
    private String priority;
    private Long votes;
    private String fixVersions;
    private String affectedVersions;
    //private String comments;
    private String issueLinks;
    //private String attachments;
    //private String worklogs;
    private Long watchers;
    //private Long timeTracking;
    //private String subtasks;
    //private String operations;
    //private String self;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    //private LocalDateTime dueDate;
    private String workFlow;
    private String issueFields;
    private String reporterEmail;
    private String assigneeEmail;
    private String changelogRecord;
}
