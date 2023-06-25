package com.robot.gs.jira.integration;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.DataProcessor;
import com.robot.gs.udesk.integration.ConditionFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ApiRecordJira {
    private final ApiNameEnum apiName;
    private final DataProcessor dataProcessor;
    private final LocalDate conditionDate;
}
