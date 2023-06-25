package com.robot.gs.ticket.http.kingdee.handler;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.kingdee.config.ServiceGoConfig;
import com.robot.gs.ticket.http.kingdee.model.RecordQueryNoListModel;
import com.robot.gs.ticket.http.kingdee.model.RecordQuerySessionIdModel;
import com.robot.gs.ticket.http.kingdee.model.RecordQueryViewDetailModel;
import com.robot.gs.ticket.http.kingdee.model.common.QueryNoListData;
import com.robot.gs.ticket.http.kingdee.model.common.QueryViewDetailData;
import com.robot.gs.ticket.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.robot.gs.common.ApiNameEnum.*;


@Slf4j
public class RecordHandler {
    protected final ServiceGoConfig serviceGoConfig;
    private HashMap<String, String> headers;

    public RecordHandler(ServiceGoConfig serviceGoConfig) {
        this.serviceGoConfig = serviceGoConfig;
        this.headers = getHeaders();
    }

    // kingdee请求前先调用金蝶登录接口获取sessionId
    public String querySessionId() {
        RecordQuerySessionIdModel querySessionIdModel =
                RecordQuerySessionIdModel.builder()
                        .Acctid(serviceGoConfig.getSessionIdAcctid())
                        .userName(serviceGoConfig.getSessionIdUserName())
                        .password(serviceGoConfig.getSessionIdPassword())
                        .lcid(serviceGoConfig.getSessionIdLcid())
                        .build();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(serviceGoConfig.getScheme())
                .host(serviceGoConfig.getHost())
                .addPathSegment(serviceGoConfig.getPathSegment1())
                .addPathSegment(serviceGoConfig.getPathSegment2())
                .addQueryParameter("servId", serviceGoConfig.getSessionIdServId())
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .build();
        String resp = HttpUtil.post(
                new Gson().toJson(querySessionIdModel), String.valueOf(url));
        if (null == resp) {
            log.error("Query SessionId result is null");
            throw new RuntimeException("failed to query the SessionId data from kingdee client");
        }
        //三种error：一是无json格式的参数异常；二是有json格式的参数异常"actionname":"ShowErrMsg"；三是有json格式的参数异常"IsSuccessByAPI":false
        String sessionId = null;
        try {
            sessionId = JSONObject.parseObject(resp).getString("KDSVCSessionId");
        } catch (Exception e) {
            log.error("非JSON格式，Query SessionId 参数异常");
            log.error(resp);
            throw new RuntimeException("failed to query the SessionId data from kingdee client");
        }
        if (sessionId == null) {
            log.error("JSON格式，Query SessionId 参数异常");
            log.error(resp);
            throw new RuntimeException("failed to query the SessionId data from kingdee client");
        }
        return sessionId;
    }

    // kingdee单据list查询接口
    public String queryNoList(RecordQueryNoListModel recordQueryNoListModel) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme(serviceGoConfig.getScheme())
                .host(serviceGoConfig.getHost())
                //.port(8800)
                .addPathSegment(serviceGoConfig.getPathSegment1())
                .addPathSegment(serviceGoConfig.getPathSegment2())
                .addQueryParameter("servId", serviceGoConfig.getNoListServId())
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .build();
        String resp = HttpUtil.post(
                new Gson().toJson(recordQueryNoListModel), String.valueOf(url), headers);
        return resp;
    }

    // kingdee查看接口
    public String queryViewDetail(RecordQueryViewDetailModel recordQueryViewDetailModel) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme(serviceGoConfig.getScheme())
                .host(serviceGoConfig.getHost())
                //.port(8800)
                .addPathSegment(serviceGoConfig.getPathSegment1())
                .addPathSegment(serviceGoConfig.getPathSegment2())
                .addQueryParameter("servId", serviceGoConfig.getViewDetailServId())
                .addQueryParameter("sysId", serviceGoConfig.getSysId())
                .addQueryParameter("serialNo", UUID.randomUUID().toString())
                .build();
        String resp = HttpUtil.post(
                new Gson().toJson(recordQueryViewDetailModel), String.valueOf(url), headers);
        return resp;
    }

    //获得Headers
    public HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("kdservice-sessionid", querySessionId());
        return headers;
    }

    //获得当天所有FBillNoList or FNumberList
    public List<String> getNoList(ApiNameEnum apiObjectName, LocalDate conditionDate) {
        int startRow = 0;
        int limit = 5000;
        List<String> stringNoList = new ArrayList<>();
        //物料单去重,每批次数据中去重，但是不同批次可能会产生重复数据，数仓中再group by去重
        HashSet<String> partStringset = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String queryStartDate = formatter.format(conditionDate).concat("T00:00:00.000");
        //测试用，获取少量数据
        //String queryEndDate = formatter.format(conditionDate).concat("T16:00:00.000");
        String queryEndDate = formatter.format(conditionDate.plusDays(1)).concat("T00:00:00.000");
        String FieldKeys = "";
        String FilterString = "";
        //传入FieldKeys
        //传入FilterString，拿到每天数据
        //FCreateDate->FModifyDate->FApproveDate->FCancelDate
        if (apiObjectName == KINGDEE_BD_MATERIAL) {
            FieldKeys = "FNumber";
            //首日全量，删选符合规则的成品物料信息
//            FilterString = "SUBSTRING(FNumber,1,1) = 'C' and (LEN(FNumber) = 15 or LEN(FNumber) = 13 or LEN(FNumber) = 12)";
            //每日同步
            FilterString = "SUBSTRING(FNumber,1,1) = 'C' and (LEN(FNumber) = 15 or LEN(FNumber) = 13 or LEN(FNumber) = 12) and (("
                    .concat("FCreateDate >= '").concat(queryStartDate).concat("' and FCreateDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FModifyDate >= '").concat(queryStartDate).concat("' and FModifyDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FApproveDate >= '").concat(queryStartDate).concat("' and FApproveDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FForbidDate >= '").concat(queryStartDate).concat("' and FForbidDate < '").concat(queryEndDate).concat("'))");
        } else if (apiObjectName == KINGDEE_SAL_OUTSTOCK || apiObjectName == KINGDEE_SAL_RETURNSTOCK) {
            FieldKeys = "FBillNo";
            //SAL_OUTSTOCK只取标准销售出库单和翻新机销售出库单，在数仓过滤，此处不过滤；FBillTypeID翻新机销售出库单-62de7869651105；标准销售出库单-ad0779a4685a43a08f08d2e42d7bf3e9
            //首日全量
//            FilterString = "";
            FilterString = "("
                    .concat("FDate >= '").concat(queryStartDate).concat("' and FDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FCreateDate >= '").concat(queryStartDate).concat("' and FCreateDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FModifyDate >= '").concat(queryStartDate).concat("' and FModifyDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FApproveDate >= '").concat(queryStartDate).concat("' and FApproveDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FCancelDate >= '").concat(queryStartDate).concat("' and FCancelDate < '").concat(queryEndDate).concat("')");
        } else {
            FieldKeys = "FBillNo";
            //首日全量
//            FilterString = "";
            //每日同步
            FilterString = "("
                    .concat("FCreateDate >= '").concat(queryStartDate).concat("' and FCreateDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FModifyDate >= '").concat(queryStartDate).concat("' and FModifyDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FApproveDate >= '").concat(queryStartDate).concat("' and FApproveDate < '").concat(queryEndDate).concat("') or (")
                    .concat("FCancelDate >= '").concat(queryStartDate).concat("' and FCancelDate < '").concat(queryEndDate).concat("')");
        }
        boolean flag = true;
        labelSessionId:
        while (flag) {
            RecordQueryNoListModel querySearchModel =
                    RecordQueryNoListModel.builder()
                            .data(QueryNoListData.builder()
                                    .FormId(apiObjectName.getName())
                                    .FieldKeys(FieldKeys)
                                    .FilterString(FilterString)
                                    .OrderString("")
                                    .TopRowCount(0)
                                    .StartRow(startRow)
                                    .Limit(limit)
                                    .SubSystemId("")
                                    .build())
                            .build();
            String respNo = queryNoList(querySearchModel);
            if (null == respNo) {
                log.error("Query NoList result is null");
                throw new RuntimeException("failed to query the NoList data from kingdee client");
            }
            JSONArray jsonArrayNo = JSONArray.parse(respNo);
            int datasize = 0;
            for (int i = 0; i < jsonArrayNo.size(); i++) {
                JSONArray jsonArrayOut = JSONArray.parse(String.valueOf(jsonArrayNo.get(i)));
                JSONArray jsonArrayIn = JSONArray.parse(String.valueOf(jsonArrayOut));
                for (int j = 0; j < jsonArrayIn.size(); j++) {
                    Object jsonArrayResp = jsonArrayIn.get(j);
                    //sessionId过期有json格式的"Result"的"IsSuccess":false&"MsgCode":1
                    if (jsonArrayResp.toString().contains("Result")) {
                        JSONObject result = null;
                        try {
                            result = JSONObject.parseObject(jsonArrayResp.toString()).getJSONObject("Result");
                            if (result != null) {
                                Boolean isSuccess = result.getJSONObject("ResponseStatus").getBoolean("IsSuccess");
                                Integer msgCode = result.getJSONObject("ResponseStatus").getInteger("MsgCode");
                                if (isSuccess != true && msgCode == 1) {
                                    headers.put("kdservice-sessionid", querySessionId());
                                    continue labelSessionId;
                                }
                            }
                        } catch (Exception e) {
                            log.error("接口数据非JSON格式，解析失败");
                        }
                    }
                    partStringset.add(jsonArrayResp.toString());
                    datasize++;
                }
            }
            if (datasize < limit) {
                flag = false;
                stringNoList.addAll(partStringset);
                partStringset.clear();
                log.info("Download BillNos:" + stringNoList.size() + " rows " + "data");
            } else {
                stringNoList.addAll(partStringset);
                startRow = startRow + limit;
                partStringset.clear();
            }
        }
        return stringNoList;
    }


    //拿到pageSize行ViewDetailList
    public List<String> getFixViewDetailList(ApiNameEnum apiObjectName, List<String> stringNoList, int pageSize,
                                             int startViewDetailList, int totalCount) throws InterruptedException {
        List<String> stringViewDetailList = new ArrayList<>();
        if (totalCount < startViewDetailList + pageSize) {
            pageSize = totalCount - startViewDetailList;
        }
        for (int i = startViewDetailList; i < startViewDetailList + pageSize; i++) {
            String No = stringNoList.get(i);
            RecordQueryViewDetailModel viewDetailModel =
                    RecordQueryViewDetailModel.builder()
                            .formid(apiObjectName.getName())
                            .data(QueryViewDetailData.builder()
                                    .CreateOrgId(0)
                                    .Number(No)
                                    .Id("")
                                    .IsSortBySeq("false")
                                    .build())
                            .build();
            Boolean flag = true;
            int retryCountF1 = 0;
            int retryCountF2 = 0;
            while (flag) {
                String respViewDetail = queryViewDetail(viewDetailModel);
                //四种error：一是无json格式的网关异常；二是有json格式的接口调用异常；三是有json格式的"Result"的"IsSuccess":false；四是sessionId过期有json格式的"Result"的"IsSuccess":false&"MsgCode":1
                JSONObject result = null;
                //处理异常一：重试，重试失败赋null
                //处理异常二：重试，失败本身为null
                try {
                    result = JSONObject.parseObject(respViewDetail).getJSONObject("Result");
                } catch (Exception ef) {
                    log.error("接口数据非JSON格式，解析失败");
                }
                try {
                    if (result != null) {
                        Boolean isSuccess = result.getJSONObject("ResponseStatus").getBoolean("IsSuccess");
                        Integer msgCode = result.getJSONObject("ResponseStatus").getInteger("MsgCode");
                        if (isSuccess != true && msgCode == 1) {
                            headers.put("kdservice-sessionid", querySessionId());
                            continue;
                        } else if (isSuccess != true) {
                            //处理异常三：重试，失败Result:ResponseStatus:IsSuccess:false
                            retryCountF2++;
                            log.error("retry qurey: " + retryCountF2 + " times");
                            Thread.sleep(5000);
                            if (retryCountF2 > 10) {
                                log.error("It has been retried " + retryCountF2 + "th times ,reach the " +
                                        "upper " +
                                        "limit");
                                log.error("Result:ResponseStatus:IsSuccess:false，获取数据失败");
                                throw new RuntimeException("获取数据失败");
                            } else {
                                continue;
                            }
                        }
                    } else {
                        retryCountF1++;
                        log.error("retry qurey: " + retryCountF1 + " times");
                        Thread.sleep(5000);
                        if (retryCountF1 > 10) {
                            log.error("It has been retried " + retryCountF1 + "th times ,reach the " +
                                    "upper " +
                                    "limit");
                            log.error("接口数据非JSON格式或接口调用异常，获取数据失败");
                            throw new RuntimeException("获取数据失败");
                        } else {
                            continue;
                        }
                    }
                } catch (RuntimeException e) {
                    log.error(No + " ：获取数据失败");
                    JSONObject errorJsonObject = new JSONObject();
                    JSONObject errorResultOJsonObject = new JSONObject();
                    JSONObject errorResultIJsonObject = new JSONObject();
                    JSONObject errorResultRJsonObject = new JSONObject();
                    errorJsonObject.put("Result", errorResultOJsonObject);
                    errorResultOJsonObject.put("Result", errorResultIJsonObject);
                    errorResultIJsonObject.put("Id", null);
                    if (apiObjectName == KINGDEE_BD_MATERIAL) {
                        errorResultIJsonObject.put("Number", No);
                    } else {
                        errorResultIJsonObject.put("BillNo", No);
                    }
                    errorResultIJsonObject.put("ResultRemark", errorResultRJsonObject);
                    errorResultRJsonObject.put("ErrorBillNo", No);
                    errorResultRJsonObject.put("ErrorBillType", apiObjectName.getName());
                    errorResultRJsonObject.put("ErrorData", respViewDetail);
                    respViewDetail = errorJsonObject.toJSONString();
                }
                flag = false;
                stringViewDetailList.add(respViewDetail);
            }
        }
        return stringViewDetailList;
    }
}
