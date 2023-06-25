package com.robot.gs.ticket.http.udesk.enums;

/**
 * api url 枚举.
 */
public enum ApiUrlEnum {
  RECORD_CREATE("/v1/data", "创建记录"),
  FILTER_QUERY("/v1/filters","查询过滤器"),
  RECORD_QUERY("/v1/data","查询记录"),
  RECORD_UPDATE("/v1/data","更新记录"),
  RECORD_DELETE("/v1/data","删除记录"),
  RECORDS_QUERY("/v1/datas","查询记录列表"),
  RECORDS_SEARCH_QUERY("/v1/datas/search","搜索查询记录"),
  ATTACHMENT_UPLOAD("/v1/fileField/attachments","文件、图片字段上传"),
  RECORDS_SCROLL_SEARCH_QUERY("/v1/datas/scrollSearch","滚动搜索查询记录");


  private String value;
  private String description;


  ApiUrlEnum(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}