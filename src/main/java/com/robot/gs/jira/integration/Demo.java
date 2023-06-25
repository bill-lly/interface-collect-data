package com.robot.gs.jira.integration;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.atlassian.util.concurrent.Promise;

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Demo {
  public static void main(String[] args) {
    Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
            .setDateFormat(DateFormat.LONG)
            .create();
    //sellservic,softreq,zc
    JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
        .createWithBasicHttpAuthentication(URI.create("http://180.169.129.210:10080/"), "jirabot", "Gaussian123");
    SearchRestClient searchClient = jiraRestClient.getSearchClient();
    //String jql = "project = ZC and updated > startOfDay(-1) and updated < startOfDay(0)";
    String jql = "project = qd and updated > '2022-12-29' and updated < '2022-12-30'";
    //SearchResult searchResult = searchClient.searchJql(jql, -1,0, null).claim();//(jql, 10, 10).claim();
    SearchResult searchResult = searchClient.searchJql(jql).claim();
    int total = searchResult.getTotal();
    System.out.println(total);
    //转化为字符串
    //    IssueRestClient searchIssueClient =  jiraRestClient.getIssueClient();
    //    Issue issue = searchIssueClient.getIssue("ZC-53440").claim();
    //    ArrayList<IssueField> issueFieldList = (ArrayList<IssueField>) issue.getFields();
    //    issueFieldList.sort(
    //            Comparator.comparing(issueField -> issueField.getId())
    //    );
    //    List<String> issueFieldJsonList = new ArrayList<String>();
    //    issueFieldList.forEach(issueField -> {
    //      issueFieldJsonList.add(gson.toJson(issueField).toString());
    //    });
    //    String jsonStr = issueFieldJsonList.toString();
    //    System.out.println(jsonStr);
    //String s = "he'l'l'o";
    //String snew = s.replace("\'", "\"");
    //zc 263 262
    //测试json
    //String workFlowResult = ;
    //gson.toJson(workFlowResult).replace("\\", "");
  }
}
