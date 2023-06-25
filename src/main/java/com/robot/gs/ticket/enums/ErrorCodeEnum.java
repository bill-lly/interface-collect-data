package com.robot.gs.ticket.enums;

public enum ErrorCodeEnum {

  CALL_FAILED(-10001,"调用第三方接口失败");

  private Integer errorCode;
  private String errorMessage;

  ErrorCodeEnum(Integer errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }

  public Integer getErrorCode() {
    return errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

}
