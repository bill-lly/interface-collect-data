package com.robot.gs.kingdee;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.DataProcessor;
import com.robot.gs.kingdee.processor.KingdeeDataProcessor;
import com.robot.gs.udesk.integration.ConditionFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class ApiRecordKingdee {
    private final ApiNameEnum apiName;
    private final KingdeeDataProcessor dataProcessor;
    private final LocalDate conditionDate;
}
