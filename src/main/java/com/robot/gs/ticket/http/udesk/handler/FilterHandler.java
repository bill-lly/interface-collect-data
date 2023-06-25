package com.robot.gs.ticket.http.udesk.handler;

import com.robot.gs.ticket.http.udesk.config.ServiceGoConfig;
import com.robot.gs.ticket.http.udesk.enums.ApiUrlEnum;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import com.robot.gs.ticket.utils.HttpUtil;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FilterHandler extends AbstractHandler {
  public FilterHandler(ServiceGoConfig serviceGoConfig) {
    super(serviceGoConfig);
  }

  public ResultModel query(String objectApiName) {
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("objectApiName", objectApiName);
    paramMap.put("userId", serviceGoConfig.getUserId());
    String uri = super.authorize(
        ApiUrlEnum.FILTER_QUERY.getValue(), paramMap);
    Request request = new Request.Builder()
        .url(uri)
        .build();
    String resp = HttpUtil.get(request);
    return resultModel(resp);
  }


}
