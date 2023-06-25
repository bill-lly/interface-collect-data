package com.robot.gs.ticket.http.udesk.model;

import com.robot.gs.ticket.http.udesk.model.common.FieldData;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordUpdateModel {
  private String objectApiName;
  private int id;
  // 指定外部唯一字段API名称.
  //如果uniqueFieldApiName与uniqueFieldValue为空，则ID必填
  private String uniqueFieldApiName;
  private String uniqueFieldValue;
  private List<FieldData> fieldDataList;
}
