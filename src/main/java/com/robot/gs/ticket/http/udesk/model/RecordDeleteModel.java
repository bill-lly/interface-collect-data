package com.robot.gs.ticket.http.udesk.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDeleteModel {
  private String objectApiName;
  private int id;
}
