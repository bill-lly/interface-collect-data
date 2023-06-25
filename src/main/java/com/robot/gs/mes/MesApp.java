package com.robot.gs.mes;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.udesk.integration.HiveDataSource;
import com.robot.gs.mes.dateProcessor.MaterialWHKingdeeProcessor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class MesApp {
    private final static String Database = "gs_ods";
    private final static String TableName = "ods_i_mes_material_wh_kingdee";

    private CommandArgs args;

    public MesApp(CommandArgs args) {
        this.args = args;
    }

    public Integer process() {
        List<ApiRecordMes> recordList = createRecordList(args);
        try (
                HiveDataSource ds = new HiveDataSource("/hive.properties");
                Connection conn = ds.getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute("set hive.exec.dynamic.partition=true");
            stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
            stmt.execute("set hive.exec.max.dynamic.partitions.pernode = 1000");
            stmt.execute("set hive.exec.max.dynamic.partitions=1000");
            Properties properties = loadProperties("/mes.properties");
            for (ApiRecordMes apiRecordMes : recordList) {
                if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordMes.getApiName().getName())) {
                    continue;
                }
                MesClient mesClient = new MesClient(properties, apiRecordMes.getApiName(),
                        apiRecordMes.getMesDataProcessor());
                try {
                    mesClient.getAndSaveData(stmt,apiRecordMes.getConditionDate());
                } catch (SQLException e) {
                    log.error("Can not save data", e);
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("Can not download and save mes data", e);
            return 1;
        }
        return 0;
    }

    private List<ApiRecordMes> createRecordList(CommandArgs args) {
        List<ApiRecordMes> apiRecordMeskList = Arrays.asList(
                new ApiRecordMes(ApiNameEnum.RrocessReport,
                        new MaterialWHKingdeeProcessor(ApiNameEnum.RrocessReport,Database,TableName,args.getBizDate(),true)
                        ,args.getBizDate())
        );
        return apiRecordMeskList;
    }
}
