package com.robot.gs.ticket.http.jira.handler;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.google.gson.Gson;
import com.robot.gs.ticket.http.jira.config.ServiceGoConfig;
import com.robot.gs.ticket.http.udesk.enums.ApiUrlEnum;
import com.robot.gs.ticket.http.udesk.handler.AbstractHandler;
import com.robot.gs.ticket.http.udesk.model.RecordQueryScrollSearchModel;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import com.robot.gs.ticket.utils.HttpUtil;
import com.robot.gs.udesk.integration.Helper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

public class RecordHandler {
    protected final ServiceGoConfig serviceGoConfig;
    private static SearchRestClient searchClient = null;
    public RecordHandler(ServiceGoConfig serviceGoConfig) {
        this.serviceGoConfig = serviceGoConfig;
    }

    private SearchRestClient getSearchClient() {
        if(searchClient == null){
            synchronized (SearchRestClient.class) {
                if(searchClient == null) {
                    JiraRestClient jiraRestClient = new AsynchronousJiraRestClientFactory()
                            .createWithBasicHttpAuthentication(URI.create(serviceGoConfig.getApiUrlPrefix()), serviceGoConfig.getUserName(), serviceGoConfig.getPassWord());
                    searchClient = jiraRestClient.getSearchClient();
                }
            }
        }
        return searchClient;
    }

    public SearchResult queryList(String jql, int maxResults, int startAt, Set<String> fields) {
        SearchRestClient searchClient = getSearchClient();
        SearchResult searchResult = searchClient.searchJql(jql, maxResults, startAt, fields).claim();
        return searchResult;
    }

    public int getTotal(String jql) {
        SearchRestClient searchClient = getSearchClient();
        SearchResult searchResult = searchClient.searchJql(jql).claim();
        return searchResult.getTotal();
    }

    /**
     * 查询jira工作流数据接口.
     *
     * @return
     */
    public String queryWorkFlow(String issueKey) {
        String uri = serviceGoConfig.getApiUrlPrefix() + serviceGoConfig.getApiUrlWorkflow()
                + issueKey + "/1";
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((serviceGoConfig.getUserName() + ":" + serviceGoConfig.getPassWord()).getBytes()))
                .url(uri)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }

    /**
     * 查询jira工作流节点状态数据接口.
     *
     * @return
     */
    public String queryWorkFlowStatus() {
        String uri = serviceGoConfig.getApiUrlPrefix() + serviceGoConfig.getApiUrlWorkflowStatus();
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((serviceGoConfig.getUserName() + ":" + serviceGoConfig.getPassWord()).getBytes()))
                .url(uri)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }

    /**
     * 查询jira改动记录数据接口.
     *
     * @return
     */
    public String queryChangelogRecord(String issueKey) {
        String uri = serviceGoConfig.getApiUrlPrefix() + String.format(serviceGoConfig.getApiUrlChangelogRecord(), issueKey);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((serviceGoConfig.getUserName() + ":" + serviceGoConfig.getPassWord()).getBytes()))
                .url(uri)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }

}
