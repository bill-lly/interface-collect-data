package com.robot.gs.ticket.http.udesk.model;

import com.robot.gs.ticket.http.udesk.model.common.FieldData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordCreateModel {
  private String objectApiName;
  private List<FieldData> fieldDataList;
}
