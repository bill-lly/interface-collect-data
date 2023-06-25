package com.robot.gs.udesk.integration;

import com.robot.gs.ticket.http.udesk.model.common.Condition;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public abstract class ConditionFactory {
  protected LocalDate startDate;

  public ConditionFactory(LocalDate startDate) {
    this.startDate = null == startDate ? LocalDate.now().minusDays(1) : startDate;
  }

  public ConditionFactory(String startDate) {
    this.startDate = null == startDate ? LocalDate.now().minusDays(1) : LocalDate.parse(startDate);
  }

  public abstract List<Condition> create();
}
