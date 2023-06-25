package com.robot.gs.ticket.exceptions;


public final class BussinessException extends RuntimeException {

  private int errorCode;

  public BussinessException(int errorCode) {
    this.errorCode = errorCode;
  }

  public BussinessException(int errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public BussinessException(String message, Throwable cause, int errorCode) {
    super(message, cause);
    this.errorCode = errorCode;
  }

  public BussinessException(Throwable cause, int errorCode) {
    super(cause);
    this.errorCode = errorCode;
  }

  public BussinessException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace, int errorCode) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.errorCode = errorCode;
  }
}
