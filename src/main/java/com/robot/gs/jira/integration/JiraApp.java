package com.robot.gs.jira.integration;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.udesk.integration.HiveDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class JiraApp {
    private final static String GS_ODS ="gs_ods";
    private final static String HIVE_TABLE_JIRA_SELLSERVIC = "ods_i_jira_sellservic";
    private final static String HIVE_TABLE_JIRA_SOFTREQ = "ods_i_jira_softreq";
    private final static String HIVE_TABLE_JIRA_ZC = "ods_i_jira_zc";
    private final static String HIVE_TABLE_JIRA_QD = "ods_i_jira_qd";

    private CommandArgs args;

    public JiraApp(CommandArgs args) {
        this.args = args;
    }

    public Integer process() {
        List<ApiRecordJira> recordList = createRecordList(args);
        try (HiveDataSource ds = new HiveDataSource("/hive.properties");
             Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("set hive.exec.dynamic.partition=true");
            stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
            stmt.execute("set hive.exec.max.dynamic.partitions.pernode = 1000");
            stmt.execute("set hive.exec.max.dynamic.partitions=1000");
            Properties properties = loadProperties("/jira.properties");
            for (ApiRecordJira apiRecordJira : recordList) {
                if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordJira.getApiName().getName())) {
                    continue;
                }
                JiraClient jiraClient = new JiraClient(properties, apiRecordJira.getApiName(),
                        apiRecordJira.getDataProcessor()
                );

                jiraClient.getAndSaveData(stmt, apiRecordJira.getConditionDate());
            }
        } catch (Exception e) {
            log.error("Can not download and save jira data", e);
            return 1;
        }
        return 0;
    }

    private List<ApiRecordJira> createRecordList(CommandArgs args) {
        List<ApiRecordJira> apiRecordJiraList = Arrays.asList(
                new ApiRecordJira(
                        ApiNameEnum.JIRA_SELLSERVIC,
                        new JiraDataProcessor(ApiNameEnum.JIRA_SELLSERVIC, GS_ODS, HIVE_TABLE_JIRA_SELLSERVIC, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordJira(
                        ApiNameEnum.JIRA_SOFTREQ,
                        new JiraDataProcessor(ApiNameEnum.JIRA_SOFTREQ, GS_ODS, HIVE_TABLE_JIRA_SOFTREQ, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordJira(
                        ApiNameEnum.JIRA_ZC,
                        new JiraDataProcessor(ApiNameEnum.JIRA_ZC, GS_ODS, HIVE_TABLE_JIRA_ZC, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordJira(
                        ApiNameEnum.JIRA_QD,
                        new JiraDataProcessor(ApiNameEnum.JIRA_QD, GS_ODS, HIVE_TABLE_JIRA_QD, args.getBizDate(), true),
                        args.getBizDate())
        );
        return apiRecordJiraList;
    }

}
