package com.robot.gs.ticket.http.udesk.enums.order;

import java.util.Optional;


public enum StateEnum {
  UNASSIGNED(0, "待分配"),
  ASSIGNED(1, "已分配"),
  BOOKED(2, "已预约"),
  DEPARTURED(3, "已出发"),
  ARRIVED(4, "已到达"),
  FINISHED(5, "已完工"),
  CLOSED(6, "已关单"),
  COMPLETED(7, "已完成"),
  UNBOOKED(8, "待预约");

  StateEnum(int code, String description) {
    this.code = code;
    this.description = description;
  }

  private int code;
  private String description;

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static Optional<StateEnum> getEnumByDescription(String description) {
    StateEnum[] values = values();
    for (StateEnum stateEnum : values) {
      if (stateEnum.description.equals(description)) {
        return Optional.of(stateEnum);
      }
    }
    return Optional.empty();
  }
}
