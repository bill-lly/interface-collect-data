package com.robot.gs.ticket.http.udesk.handler;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.robot.gs.ticket.http.udesk.config.ServiceGoConfig;
import com.robot.gs.ticket.http.udesk.enums.ApiUrlEnum;
import com.robot.gs.ticket.http.udesk.model.RecordCreateModel;
import com.robot.gs.ticket.http.udesk.model.RecordQueryScrollSearchModel;
import com.robot.gs.ticket.http.udesk.model.RecordQuerySearchModel;
import com.robot.gs.ticket.http.udesk.model.RecordUpdateModel;
import com.robot.gs.ticket.http.udesk.model.common.ResultModel;
import com.robot.gs.ticket.utils.HttpUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class RecordHandler extends AbstractHandler {

  private static final ImmutableList supportFileType =
      ImmutableList
          .of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "png", "jpg", "jpeg", "gif");

  public RecordHandler(ServiceGoConfig serviceGoConfig) {
    super(serviceGoConfig);
  }

  /**
   * 创建udesk记录接口.
   *
   * @param recordCreateModel 创建记录对象
   * @return
   */
  public ResultModel create(RecordCreateModel recordCreateModel) {
    String uri = super.authorize(
        ApiUrlEnum.RECORD_CREATE.getValue());
    String resp = HttpUtil.post(
        new Gson().toJson(recordCreateModel), uri);
    return resultModel(resp);
  }

  /**
   * 查询udesk记录接口.
   *
   * @param recordId 创建记录对象
   * @return
   */
  public ResultModel query(Integer recordId, String objectApiName) {
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("id", String.valueOf(recordId));
    paramMap.put("objectApiName", objectApiName);
    String uri = super.authorize(
        ApiUrlEnum.RECORD_QUERY.getValue(), paramMap);
    Request request = new Request.Builder()
        .url(uri)
        .build();
    String resp = HttpUtil.get(request);
    return resultModel(resp);
  }

  /**
   * 查询udesk记录接口.
   *
   * @param uniqueFieldApiName 外部唯一字段API名称
   * @param uniqueFieldValue   对应的外部唯一字段值
   * @return
   */
  public ResultModel query(String uniqueFieldApiName, String uniqueFieldValue,String objectApiName) {
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("objectApiName", objectApiName);
    paramMap.put("uniqueFieldApiName", uniqueFieldApiName);
    paramMap.put("uniqueFieldValue", uniqueFieldValue);
    String uri = super.authorize(
            ApiUrlEnum.RECORD_QUERY.getValue(), paramMap);
    Request request = new Request.Builder()
            .url(uri)
            .build();
    String resp = HttpUtil.get(request);
    return resultModel(resp);
  }

  public ResultModel update(RecordUpdateModel recordUpdateModel) {
    String uri = super.authorize(
        ApiUrlEnum.RECORD_UPDATE.getValue());
    String resp = HttpUtil.put(
        new Gson().toJson(recordUpdateModel), uri);
    return resultModel(resp);
  }

  public ResultModel delete(Integer recordId, String objectApiName) {
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("id", String.valueOf(recordId));
    paramMap.put("objectApiName", objectApiName);
    String uri = super.authorize(
        ApiUrlEnum.RECORD_DELETE.getValue(), paramMap);
    Request request = new Request.Builder()
        .delete()
        .url(uri)
        .build();
    String resp = HttpUtil.delete(request);
    return resultModel(resp);
  }


  public ResultModel queryList(Integer page, Integer pageSize,
                               Integer filterId, String objectApiName) {
    page = page == null ? page = 1 : page;
    pageSize = pageSize == null ? pageSize = 10 : pageSize;
    Preconditions.checkNotNull(filterId, "filter id is empty");
    Preconditions.checkArgument(
        !ObjectUtils.isEmpty(objectApiName), "objectApiName is empty");
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("pageNum", String.valueOf(page));
    paramMap.put("pageSize", String.valueOf(pageSize));
    paramMap.put("filterId", String.valueOf(filterId));
    paramMap.put("objectApiName", objectApiName);
    String uri = super.authorize(
        ApiUrlEnum.RECORDS_QUERY.getValue(), paramMap);
    Request request = new Request.Builder()
        .url(uri)
        .build();
    String resp = HttpUtil.get(request);
    return resultModel(resp);
  }

  public ResultModel queryListBySearch(RecordQuerySearchModel recordQuerySearchModel) {
    String uri = super.authorize(
        ApiUrlEnum.RECORDS_SEARCH_QUERY.getValue());
    String resp = HttpUtil.post(
        new Gson().toJson(recordQuerySearchModel), uri);
    return resultModel(resp);
  }


  public ResultModel queryScrollListBySearch(RecordQueryScrollSearchModel recordQuerySearchModel) {
    String uri = super.authorize(
        ApiUrlEnum.RECORDS_SCROLL_SEARCH_QUERY.getValue());
    String resp = HttpUtil.post(
        new Gson().toJson(recordQuerySearchModel), uri);
    return resultModel(resp);
  }

  public void uploadAttachMents(Integer recordId, String objectApiName,
                                String fieldApiName, List<String> urls) {
    HashMap<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("dataId", String.valueOf(recordId));
    paramMap.put("objectApiName", objectApiName);
    paramMap.put("fieldApiName", fieldApiName);
    String uri = super.authorize(
        ApiUrlEnum.ATTACHMENT_UPLOAD.getValue(), paramMap);
    urls.forEach(url -> {
      String fileName = url.substring(url.lastIndexOf('/'));
      String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
      if (!supportFileType.contains(fileType)) {
        return;
      }
      File file = null;
      try {
        file = new File(FileUtils.getTempDirectory() + File.separator + fileName);
        FileUtils.copyURLToFile(new URL(url), file);
        HttpUtil.post(file, uri);
      } catch (IOException ioe) {
        log.error("[Service] fetch stream by url is error", ioe);
      }
    });
  }


}
