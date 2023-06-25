package com.robot.gs.udesk.WriteUdesk;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.ticket.http.udesk.config.ServiceGoConfig;
import com.robot.gs.ticket.http.udesk.handler.RecordHandler;
import com.robot.gs.udesk.WriteUdesk.common.CommonDataProcessor;
import com.robot.gs.udesk.integration.ConditionFactory;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;

import static com.robot.gs.udesk.integration.Helper.getInt;

/**
 * https://www.udesk.cn/doc/sercive/data/#_32
 */
@Slf4j
public class WriteUdeskClient {
    // prod
    private final static String DATABASE = "robotbi";
    private final static String WORK_TICKET_BEEP_ROBOT_DEPLOY = "beep_robot_deploy";
    private final static String FilterColumn = "update_time";
    private final static String orderedId = "record_id";
    // dev
//    private final static String DATABASE = "test";
//    private final static String WORK_TICKET_BEEP_ROBOT_DEPLOY = "robot_heatmap_map_info";
//    private final static String FilterColumn = "create_time";

    private final ServiceGoConfig serviceGoConfig;
    private final CommonDataProcessor commonDataProcessor;
    private final ApiNameEnum apiObjectName;

    public WriteUdeskClient(Properties properties, ApiNameEnum apiObjectName,
                            String filterIdName, CommonDataProcessor commonDataProcessor) {
        serviceGoConfig = new ServiceGoConfig();
        serviceGoConfig.setApiToken(properties.getProperty("api.token"));
        serviceGoConfig.setApiUrlPrefix(properties.getProperty("api.url.prefix"));
        serviceGoConfig.setUserId(properties.getProperty("user.id"));
        serviceGoConfig.setEmail(properties.getProperty("email"));
        serviceGoConfig.setFilterId(getInt(properties, filterIdName, 26287));

        this.commonDataProcessor = commonDataProcessor;
        this.apiObjectName = apiObjectName;
    }

    public void queryAndWriteData(Statement stmt,
                                  ConditionFactory conditionFactory) throws SQLException {
        RecordHandler recordHandler = new RecordHandler(serviceGoConfig);
        // 为防止数据量大，分页查询
        int pageSize = 100;
        int scrollId = 0;
        // 当日是否有数据
        boolean hasData = false;
        boolean flag = true;
        while (flag) {
            String querySQL = getQuerySQL(DATABASE, WORK_TICKET_BEEP_ROBOT_DEPLOY, FilterColumn, pageSize, scrollId,
                    conditionFactory,orderedId);
            log.info("\n QuerySQL : [\n" + querySQL + " ]");
            ResultSet resultSet = stmt.executeQuery(querySQL);

            int count = 0;
            while (resultSet.next()) {
                hasData=true;
                commonDataProcessor.mappingAndPutData(recordHandler,resultSet, apiObjectName);
                count++;
            }
            if (!hasData)log.warn("No records need to be updated");
            scrollId++;
            if (count == 0) {
                flag = false;
            }
        }
        log.info(apiObjectName+" record update completed");
    }

    private String getQuerySQL(String database, String tableName, String filterColumn, int pageSize, int scrollId,
                               ConditionFactory conditionFactory,String orderedId) {
        String startDate = conditionFactory.getStartDate().toString();
        String endDate = conditionFactory.getStartDate().minusDays(-1).toString();
        return "select * \n" +
                "from " + database + "." + tableName + "\n" +
                "where " + filterColumn + " >= '" + startDate + " 00:00:00" + "' \n" +
                "  and " + filterColumn + "  < '" + endDate + " 00:00:00" + "'\n" +
                "  and "+orderedId+" is not null \n"+
                "order by id \n" +
                "limit " + (scrollId * pageSize) + "," + pageSize + ";\n"
                ;
    }
}
