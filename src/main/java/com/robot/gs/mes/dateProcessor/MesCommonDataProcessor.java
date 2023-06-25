package com.robot.gs.mes.dateProcessor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public abstract class MesCommonDataProcessor extends MesDataProcessor {
    protected ApiNameEnum apiNameEnum;
    protected String dbName;
    protected String tbName;
    protected LocalDate startDate;
    protected boolean hasPartition;
    protected CommonDataConverter converter;

    public MesCommonDataProcessor(ApiNameEnum apiNameEnum,
                                  String dbName, String tbName,
                                  LocalDate startDate, boolean hasPartition) {
        super(startDate);
        this.apiNameEnum = apiNameEnum;
        this.dbName = dbName;
        this.tbName = tbName;
        this.startDate = startDate;
        this.hasPartition = hasPartition;
        this.converter = createConverter(apiNameEnum);
    }

    protected abstract CommonDataConverter createConverter(ApiNameEnum apiNameEnum);

}
