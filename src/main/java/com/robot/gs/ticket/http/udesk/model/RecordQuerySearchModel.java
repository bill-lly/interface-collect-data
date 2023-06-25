package com.robot.gs.ticket.http.udesk.model;

import com.robot.gs.ticket.http.udesk.model.common.Condition;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordQuerySearchModel {
  private int pageNum;
  private int pageSize;
  private int filterId;
  private String objectApiName;
  private int judgeStrategy;
  private List<Condition> conditionList;
}
