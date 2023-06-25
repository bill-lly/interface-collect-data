package com.robot.gs.dingtalk;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.dingtalk.dateProcessor.DeptEmpDateProcessor;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiRecordDingTalk {
    private final ApiNameEnum apiName;
    private final DeptEmpDateProcessor deptEmpDateProcessor;
    private final LocalDate conditionDate;
}
