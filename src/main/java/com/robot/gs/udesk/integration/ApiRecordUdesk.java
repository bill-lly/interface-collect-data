package com.robot.gs.udesk.integration;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.DataProcessor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiRecordUdesk {
  private final String filterIdName;
  private final ApiNameEnum apiName;
  private final DataProcessor dataProcessor;
  private final ConditionFactory conditionFactory;
}
