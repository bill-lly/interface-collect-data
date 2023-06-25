package com.robot.gs.kingdee;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.kingdee.processor.*;
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
public class KingdeeApp {
//每日增量更新地址
    private final static String GS_ODS ="gs_ods";
    private final static String KINGDEE_SAL_OUTSTOCK = "ods_i_kingdee_sal_outstock";
    private final static String KINGDEE_SAL_RETURNSTOCK = "ods_i_kingdee_sal_returnstock";
    private final static String KINGDEE_PUR_REQUISITION = "ods_i_kingdee_pur_requisition";
    private final static String KINGDEE_PUR_PURCHASEORDER = "ods_i_kingdee_pur_purchaseorder";
    private final static String KINGDEE_STK_INSTOCK = "ods_i_kingdee_stk_instock";
    private final static String KINGDEE_PUR_RECEIVEBILL = "ods_i_kingdee_pur_receivebill";
    private final static String KINGDEE_BD_MATERIAL = "ods_i_kingdee_bd_material";

//首日全量更新暂存地址
//    private final static String GS_ODS = "gs_tmp";
//    private final static String KINGDEE_SAL_OUTSTOCK = "tmp_ods_i_kingdee_sal_outstock";
//    private final static String KINGDEE_SAL_RETURNSTOCK = "tmp_ods_i_kingdee_sal_returnstock";
//    private final static String KINGDEE_PUR_REQUISITION = "tmp_ods_i_kingdee_pur_requisition";
//    private final static String KINGDEE_PUR_PURCHASEORDER = "tmp_ods_i_kingdee_pur_purchaseorder";
//    private final static String KINGDEE_STK_INSTOCK = "tmp_ods_i_kingdee_stk_instock";
//    private final static String KINGDEE_PUR_RECEIVEBILL = "tmp_ods_i_kingdee_pur_receivebill";
//    private final static String KINGDEE_BD_MATERIAL = "tmp_ods_i_kingdee_bd_material";

    private CommandArgs args;

    public KingdeeApp(CommandArgs args) {
        this.args = args;
    }

    public Integer process() {
        List<ApiRecordKingdee> recordList = createRecordList(args);
        try (HiveDataSource ds = new HiveDataSource("/hive.properties");
             Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("set hive.exec.dynamic.partition=true");
            stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
            stmt.execute("set hive.exec.max.dynamic.partitions.pernode = 1000");
            stmt.execute("set hive.exec.max.dynamic.partitions=1000");
            Properties properties = loadProperties("/kingdee.properties");
            for (ApiRecordKingdee apiRecordKingdee : recordList) {
                if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordKingdee.getApiName().getName())) {
                    continue;
                }
                KingdeeClient kingdeeClient = new KingdeeClient(properties, apiRecordKingdee.getApiName(),
                        apiRecordKingdee.getDataProcessor());
                try {
                    kingdeeClient.getAndSaveData(stmt, apiRecordKingdee.getConditionDate());
                } catch (SQLException e) {
                    log.error("Can not save data", e);
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            log.error("Can not download and save kingdee data", e);
            return 1;
        }
        return 0;
    }

    private List<ApiRecordKingdee> createRecordList(CommandArgs args) {
        List<ApiRecordKingdee> apiRecordKingdee = Arrays.asList(
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_BD_MATERIAL,
                        new BDMaterialProcessor(ApiNameEnum.KINGDEE_BD_MATERIAL, GS_ODS, KINGDEE_BD_MATERIAL, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_PUR_REQUISITION,
                        new PURRequisitionProcessor(ApiNameEnum.KINGDEE_PUR_REQUISITION, GS_ODS, KINGDEE_PUR_REQUISITION, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_PUR_PURCHASEORDER,
                        new PURPurchaseOrderProcessor(ApiNameEnum.KINGDEE_PUR_PURCHASEORDER, GS_ODS, KINGDEE_PUR_PURCHASEORDER, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_PUR_RECEIVEBILL,
                        new PURReceiveBillProcessor(ApiNameEnum.KINGDEE_PUR_RECEIVEBILL, GS_ODS, KINGDEE_PUR_RECEIVEBILL, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_STK_INSTOCK,
                        new STKInstockProcessor(ApiNameEnum.KINGDEE_STK_INSTOCK, GS_ODS, KINGDEE_STK_INSTOCK, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_SAL_OUTSTOCK,
                        new SALOutstockProcessor(ApiNameEnum.KINGDEE_SAL_OUTSTOCK, GS_ODS, KINGDEE_SAL_OUTSTOCK, args.getBizDate(), true),
                        args.getBizDate()),
                new ApiRecordKingdee(ApiNameEnum.KINGDEE_SAL_RETURNSTOCK,
                        new SALReturnstockProcessor(ApiNameEnum.KINGDEE_SAL_RETURNSTOCK, GS_ODS, KINGDEE_SAL_RETURNSTOCK, args.getBizDate(), true),
                        args.getBizDate())
        );
        return apiRecordKingdee;
    }


}
