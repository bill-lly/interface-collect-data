package com.robot.gs.ticket.http.udesk.model.common;

import lombok.Data;

@Data
public class ResultModel {
  private int code;
  private String message;
  private Boolean visible;
  private Paging paging;
  private Object data;
}
