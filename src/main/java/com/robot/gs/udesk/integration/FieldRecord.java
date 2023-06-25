package com.robot.gs.udesk.integration;

import lombok.Data;

@Data
public class FieldRecord {

  public FieldRecord(String fieldValue, String foreignDataName) {
    this.fieldValue = fieldValue;
    this.foreignDataName = foreignDataName;
  }

  String fieldValue;
  String foreignDataName;
}
