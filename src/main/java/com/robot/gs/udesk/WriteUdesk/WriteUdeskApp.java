package com.robot.gs.udesk.WriteUdesk;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.JDBCDataSource;
import com.robot.gs.udesk.WriteUdesk.common.CommonDataProcessor;
import com.robot.gs.udesk.WriteUdesk.processor.WorkTicketDataProcessor;
import com.robot.gs.udesk.integration.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class WriteUdeskApp {

    private CommandArgs args;

    public WriteUdeskApp(CommandArgs args) {
        this.args = args;
    }

    public Integer process() {
        List<ApiRecordUdesk> recordList = createRecordList(args);
        try (JDBCDataSource ds = new JDBCDataSource("/mysql.properties");
             Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            Properties properties = loadProperties("/udesk.properties");

            for (ApiRecordUdesk apiRecordUdesk : recordList) {
                if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordUdesk.getApiName().getName())) {
                    continue;
                }
                WriteUdeskClient writeUdeskClient = new WriteUdeskClient(properties, apiRecordUdesk.getApiName(),
                        apiRecordUdesk.getFilterIdName(), (CommonDataProcessor)apiRecordUdesk.getDataProcessor());
                try {
                    writeUdeskClient.queryAndWriteData(stmt, apiRecordUdesk.getConditionFactory());
                } catch (SQLException e) {
                    log.error("Can't put data : "+apiRecordUdesk.getApiName().getName(), e);
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("Can't put data to udesk", e);
            return 1;
        }
        return 0;
    }

    private List<ApiRecordUdesk> createRecordList(CommandArgs args) {
        List<ApiRecordUdesk> apiRecordUdeskList = Arrays.asList(
                new ApiRecordUdesk("work.order.filter.id",
                        ApiNameEnum.WORK_TICKET,
                        new WorkTicketDataProcessor(ApiNameEnum.WORK_TICKET),
                        new WorkTicketConditionFactory(args.getBizDate()))
        );
        return apiRecordUdeskList;
    }


}
