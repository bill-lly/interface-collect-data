package com.robot.gs.ticket.http.mes.handler;

import com.robot.gs.ticket.http.mes.config.ServiceGoConfig;
import com.robot.gs.ticket.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.Base64;
import java.util.UUID;

@Slf4j
public class RecordHandler {
    private final ServiceGoConfig serviceGoConfig;

    public RecordHandler(ServiceGoConfig serviceGoConfig){
        this.serviceGoConfig=serviceGoConfig;
    }

    /**
     * GET查询所有车号SN
     * servId 用 servIdModelNumber
     * @return
     */
    public String queryAllModelNumber(int startOffset,int pageSize) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(serviceGoConfig.getUrl())
                .addPathSegment("REST_REST")
                .addPathSegment("original")
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .addQueryParameter("servId", serviceGoConfig.getServIdModelNumber())
                .addQueryParameter("startOffset", ""+startOffset)
                .addQueryParameter("pageSize", ""+pageSize)
                .build();

        Request request = new Request.Builder()
                .addHeader("api_id",serviceGoConfig.getApi_id())
                .addHeader("api_key",serviceGoConfig.getApi_key())
                .url(url)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }

    /**
     * GET 根据车号查询工序汇报数据
     * 注意servId要换成servIdStockInDate
     * @return
     */
    public String queryProcessReportByModelNumber(String modelNumber) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(serviceGoConfig.getUrl())
                .addPathSegment("REST_REST")
                .addPathSegment("original")
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .addQueryParameter("servId", serviceGoConfig.getServIdByModelNumber())
                .addQueryParameter("model_numbers",modelNumber)
                .build();

        Request request = new Request.Builder()
                .addHeader("api_id",serviceGoConfig.getApi_id())
                .addHeader("api_key",serviceGoConfig.getApi_key())
                .url(url)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }

    /**
     * GET 根据时间查询物料金蝶入库数据
     *
     * @return
     */
    public String queryProcessReportByDate(String datecreatedStart,String datecreatedEnd,String startOffset,String pageSize ) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host(serviceGoConfig.getUrl())
                .addPathSegment("REST_REST")
                .addPathSegment("original")
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .addQueryParameter("servId", serviceGoConfig.getServIdByDate())
                .addQueryParameter("datecreated_start",datecreatedStart)
                .addQueryParameter("datecreated_end",datecreatedEnd)
                .addQueryParameter("startOffset",startOffset)
                .addQueryParameter("pageSize",pageSize)
                .build();

        Request request = new Request.Builder()
                .addHeader("api_id",serviceGoConfig.getApi_id())
                .addHeader("api_key",serviceGoConfig.getApi_key())
                .url(url)
                .build();
        String resp = HttpUtil.get(request);
        return resp;
    }
}
