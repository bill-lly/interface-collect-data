package com.robot.gs.jira.integration;

import com.robot.gs.ticket.utils.HttpUtil;
import okhttp3.Request;

import java.util.Base64;

public class TestQueryInterface {
    public static void main(String[] args) {
        String uri = "http://180.169.129.210:10080/rest/timestatistics/latest/config/history/SELLSERVIC-45886/1";
        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString(("jirabot" + ":" + "Gaussian123").getBytes()))
                .url(uri)
                .build();
        
        String resp = HttpUtil.get(request);
        System.out.println(resp);
    }
}
