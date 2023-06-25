package com.robot.gs.ticket.http.udesk.model;

import com.robot.gs.ticket.http.udesk.model.common.Condition;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordQueryScrollSearchModel {
  private long scrollId;
  private int pageSize;
  private int filterId;
  private String objectApiName;
  private int judgeStrategy;
  private String customJudgeLogic;
  private List<Condition> conditionList;
}
