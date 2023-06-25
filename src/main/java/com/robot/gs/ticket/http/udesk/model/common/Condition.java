package com.robot.gs.ticket.http.udesk.model.common;

import lombok.Data;

@Data
public class Condition {
  private String fieldApiName;
  private String operator;
  private String value;
}
