package com.robot.gs.kingdee.processor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.kingdee.bean.STKInstock;
import com.robot.gs.kingdee.converter.STKInstockConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
public class STKInstockProcessor extends KingdeeCommonDataProcessor {

    private final STKInstockConverter converter = new STKInstockConverter();

    public STKInstockProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
                               boolean hasPartition) {
        super(apiNameEnum, dbName, tbName, startDate, hasPartition);
    }

    @Override
    protected CommonDataConverter createConverter(ApiNameEnum apiNameEnum) {
        return null;
    }

    @Override
    public void save(Statement stmt, List<String> dataList, Boolean isFirst) throws SQLException {
        StringBuilder builder = new StringBuilder(10240);
        if (isFirst) {
            builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
        } else {
            builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
        }
        builder.append("PARTITION (pt)\n")
                .append("SELECT INLINE (ARRAY ( ");
        for (String data : dataList) {
            STKInstock e = converter.from(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getId()).append("', '")
                    .append(e.getBillNo()).append("', '")
                    .append(e.getDate()).append("', '")
                    .append(e.getFBillTypeIDName()).append("', '")
                    .append(e.getAPStatus()).append("', '")
                    .append(e.getCreatorIdName()).append("', '")
                    .append(e.getSupplyIdName()).append("', '")
                    .append(e.getCreateDate()).append("', '")
                    .append(e.getSettleIdName()).append("', '")
                    .append(e.getBusinessType()).append("', '")
                    .append(e.getFModifierIdName()).append("', '")
                    .append(e.getChargeIdName()).append("', '")
                    .append(e.getFModifyDate()).append("', '")
                    .append(e.getSupplierIdName()).append("', '")
                    .append(e.getApproverIdName()).append("', '")
                    .append(e.getStockOrgIdName()).append("', '")
                    .append(e.getOwnerIdHeadName()).append("', '")
                    .append(e.getApproveDate()).append("', '")
                    .append(e.getStockDeptIdName()).append("', '")
                    .append(e.getCancellerIdName()).append("', '")
                    .append(e.getOwnerTypeIdHead()).append("', '")
                    .append(e.getStockerGroupIdName()).append("', '")
                    .append(e.getCancelDate()).append("', '")
                    .append(e.getStockerIdName()).append("', '")
                    .append(e.getCancelStatus()).append("', '")
                    .append(e.getPurchaseOrgIdName()).append("', '")
                    .append(e.getDocumentStatus()).append("', '")
                    .append(e.getPurchaseDeptIdName()).append("', '")
                    .append(e.getDemandOrgIdName()).append("', '")
                    .append(e.getPurchaserIdName()).append("', '")
                    .append(e.getIsInterLegalPerson()).append("', '")
                    .append(e.getConfirmDate()).append("', '")
                    .append(e.getConfirmStatus()).append("', '")
                    .append(e.getSplitBillType()).append("', '")
                    .append(e.getInStockEntry()).append("', '")
                    .append(e.getResultRemark()).append("'");
            if (hasPartition) {
                builder.append(",'").append(formatter.format(startDate)).append("'");
            }
            builder.append(")").append(",");
        }
        if (builder.toString().endsWith(",")) {
            builder.delete(builder.length() - 1, builder.length())
                    .append("))");
        }
        try {
            stmt.execute(builder.toString());
            log.info(apiNameEnum + " Save data successfully");
        } catch (RuntimeException e) {
            log.error("Syntax parsed sql failed");
            throw new RuntimeException("Save error code data failed");
        }

    }
}
