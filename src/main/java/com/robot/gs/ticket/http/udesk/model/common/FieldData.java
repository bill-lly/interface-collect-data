package com.robot.gs.ticket.http.udesk.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldData {
  private String fieldApiName;
  private String fieldValue;
  private String foreignExternalFieldApiName;
}
