package com.robot.gs.mes;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.mes.dateProcessor.MesDataProcessor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiRecordMes {
    private final ApiNameEnum apiName;
    private final MesDataProcessor mesDataProcessor;
    private final LocalDate conditionDate;
}
