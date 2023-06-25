package com.robot.gs.ticket.http.udesk.handler;

import com.google.common.collect.Maps;
import com.robot.gs.ticket.http.udesk.config.ServiceGoConfig;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import com.robot.gs.ticket.http.udesk.util.EncryptUtil;
import com.robot.gs.ticket.utils.JsonUtil;
import java.time.Instant;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractHandler {

  protected final ServiceGoConfig serviceGoConfig;

  public AbstractHandler(ServiceGoConfig serviceGoConfig) {
    this.serviceGoConfig = serviceGoConfig;
  }

  protected String authorize(String apiUrl) {
    return this.authorize(apiUrl, Maps.newHashMap());
  }

  protected String authorize(String apiUrl, Map<String, String> paramMap) {
    log.info("request authorize apiUrl is{},paramMap is{}", apiUrl, paramMap);
    long currentTimeStamp = Instant.now().getEpochSecond();
    paramMap.put("timestamp", String.valueOf(currentTimeStamp));
    paramMap.put("apiToken", serviceGoConfig.getApiToken());
    paramMap.put("email", serviceGoConfig.getEmail());
    String sign = EncryptUtil.encrypt(
        serviceGoConfig.getEmail(), serviceGoConfig.getApiToken(), currentTimeStamp);
    paramMap.put("sign", sign);
    paramMap.remove("apiToken");
    String param = EncryptUtil.intervalStr(
        EncryptUtil.concatMap(paramMap)
    );
    String uri = serviceGoConfig.getApiUrlPrefix()
        + apiUrl
        + "?"
        + param;
    return uri;
  }

  protected static ResultModel resultModel(String resp) {
    return JsonUtil.deSerialize(resp,ResultModel.class);
  }

  public ServiceGoConfig getServiceGoConfig() {
    return serviceGoConfig;
  }
}
