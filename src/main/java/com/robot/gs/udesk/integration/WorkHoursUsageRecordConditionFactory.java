package com.robot.gs.udesk.integration;

import com.robot.gs.ticket.http.udesk.model.common.Condition;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class WorkHoursUsageRecordConditionFactory extends ConditionFactory{
    private final static String FILTER_FIELD ="updateTime";

    public WorkHoursUsageRecordConditionFactory(LocalDate startDate) {
        super(startDate);
    }

    public WorkHoursUsageRecordConditionFactory(String startDate) {
        super(startDate);
    }

    public WorkHoursUsageRecordConditionFactory() {
        this(LocalDate.now().minusDays(1));
    }

    @Override
    public List<Condition> create() {
        LinkedList<Condition> conditions = new LinkedList<>();
        Condition c1 = new Condition();
        c1.setFieldApiName(FILTER_FIELD);
        c1.setOperator("greater_than_eq");
        c1.setValue(startDate + " 00:00:00");
        conditions.add(c1);
        Condition c2 = new Condition();
        LocalDate endDate = startDate.plusDays(1);
        c2.setFieldApiName(FILTER_FIELD);
        c2.setOperator("less_than");
        c2.setValue(endDate + " 00:00:00");
        conditions.add(c2);
        return conditions;
    }
}
