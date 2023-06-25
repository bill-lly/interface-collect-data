package com.robot.gs.dingtalk;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.dingtalk.dateProcessor.DeptEmpDateProcessor;
import com.robot.gs.udesk.integration.HiveDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class DingTalkApp {
    /**
     * Database,TableName 无意义，在DeptEmpDateProcessor重新定义，这里只是为了DeptEmpDateProcessor的构造函数
     */
    private final static String Database = "Database";// gs_ods
    private final static String TableName = "TableName";

    private CommandArgs args;

    public DingTalkApp(CommandArgs args) {
        this.args = args;
    }

    public Integer process() {
        List<ApiRecordDingTalk> recordList = createRecordList(args);
        try (
                HiveDataSource ds = new HiveDataSource("/hive.properties");
                Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("set hive.exec.dynamic.partition=true");
            stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
            stmt.execute("set hive.exec.max.dynamic.partitions.pernode = 1000");
            stmt.execute("set hive.exec.max.dynamic.partitions=1000");
            Properties properties = loadProperties("/dingtalk.properties");
            for (ApiRecordDingTalk apiRecordEsb : recordList) {
                if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordEsb.getApiName().getName())) {
                    continue;
                }
                DingTalkClient dingTalkClient = new DingTalkClient(properties, apiRecordEsb.getApiName(),
                        apiRecordEsb.getDeptEmpDateProcessor());
                try {
                    dingTalkClient.getAndSaveData(stmt);
                } catch (SQLException e) {
                    log.error("Can not save data", e);
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("Can not download and save esb data", e);
            return 1;
        }
        return 0;
    }

    private List<ApiRecordDingTalk> createRecordList(CommandArgs args) {
        List<ApiRecordDingTalk> apiRecordEsbkList = Arrays.asList(
                new ApiRecordDingTalk(ApiNameEnum.DINGTALK,
                        new DeptEmpDateProcessor(ApiNameEnum.DINGTALK,Database,TableName,args.getBizDate(),true)
                ,args.getBizDate())
        );
        return apiRecordEsbkList;
    }
    
}
