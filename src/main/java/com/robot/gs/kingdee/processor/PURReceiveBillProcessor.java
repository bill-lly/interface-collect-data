package com.robot.gs.kingdee.processor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.kingdee.bean.PURReceiveBill;
import com.robot.gs.kingdee.converter.PURReceiveBillConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
public class PURReceiveBillProcessor extends KingdeeCommonDataProcessor {

    private final PURReceiveBillConverter converter = new PURReceiveBillConverter();

    public PURReceiveBillProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
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
            PURReceiveBill e = converter.from(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getId()).append("', '")
                    .append(e.getBillNo()).append("', '")
                    .append(e.getDate()).append("', '")
                    .append(e.getBillTypeID()).append("', '")
                    .append(e.getCreatorIdName()).append("', '")
                    .append(e.getSupplyIdName()).append("', '")
                    .append(e.getCreateDate()).append("', '")
                    .append(e.getSFSettleIdName()).append("', '")
                    .append(e.getBusinessType()).append("', '")
                    .append(e.getModifierIdName()).append("', '")
                    .append(e.getSFChargeIdName()).append("', '")
                    .append(e.getModifyDate()).append("', '")
                    .append(e.getSupplierIdName()).append("', '")
                    .append(e.getApproverIdName()).append("', '")
                    .append(e.getStockOrgIdName()).append("', '")
                    .append(e.getApproveDate()).append("', '")
                    .append(e.getReceiveDeptIdName()).append("', '")
                    .append(e.getCancellerId()).append("', '")
                    .append(e.getCancelDate()).append("', '")
                    .append(e.getReceiverIdName()).append("', '")
                    .append(e.getCancelStatus()).append("', '")
                    .append(e.getPurOrgIdName()).append("', '")
                    .append(e.getDocumentStatus()).append("', '")
                    .append(e.getOwnerTypeIdHead()).append("', '")
                    .append(e.getOwnerIdHeadName()).append("', '")
                    .append(e.getPurDeptIdName()).append("', '")
                    .append(e.getDemandOrgIdName()).append("', '")
                    .append(e.getPurGroupId()).append("', '")
                    .append(e.getPurchaserIdName()).append("', '")
                    .append(e.getNote()).append("', '")
                    .append(e.getConfirmStatus()).append("', '")
                    .append(e.getAccType()).append("', '")
                    .append(e.getPURReceiveEntry()).append("', '")
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
