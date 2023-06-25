package com.robot.gs.ticket.http.dingtalk.config;


import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "dingtalk")
public class ServiceGoConfig {
    //Query参数
    private String url;
    private String APPkey;
    private String APPSercret;
    private String sysId;
    private String TokenServId;
    private String DeptServId;
    private String EmpServId;
    private String accessToken;
    private JSONObject postBody;
}
