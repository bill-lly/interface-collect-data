package com.robot.gs.kingdee.processor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.kingdee.bean.SALReturnstock;
import com.robot.gs.kingdee.converter.SALReturnstockConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class SALReturnstockProcessor extends KingdeeCommonDataProcessor {

    private final SALReturnstockConverter converter = new SALReturnstockConverter();

    public SALReturnstockProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
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
            SALReturnstock e = converter.from(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getId()).append("', '")
                    .append(e.getBillNo()).append("', '")
                    .append(e.getFDate()).append("', '")
                    .append(e.getBillTypeIDName()).append("', '")
                    .append(e.getDocumentStatus()).append("', '")
                    .append(e.getSaleOrgIdName()).append("', '")
                    .append(e.getSledeptidName()).append("', '")
                    .append(e.getRetcustIdName()).append("', '")
                    .append(e.getReturnReason()).append("', '")
                    .append(e.getHeadLocId()).append("', '")
                    .append(e.getSaleGroupIdName()).append("', '")
                    .append(e.getSalesManIdName()).append("', '")
                    .append(e.getStockOrgIdName()).append("', '")
                    .append(e.getStockDeptIdName()).append("', '")
                    .append(e.getStockerGroupIdName()).append("', '")
                    .append(e.getStockerIdName()).append("', '")
                    .append(e.getBussinessType()).append("', '")
                    .append(e.getReceiveCustIdName()).append("', '")
                    .append(e.getFLinkMan()).append("', '")
                    .append(e.getReceiveAddress()).append("', '")
                    .append(e.getReceiveCusContact()).append("', '")
                    .append(e.getSettleCustIdName()).append("', '")
                    .append(e.getFLinkPhone()).append("', '")
                    .append(e.getPayCustIdName()).append("', '")
                    .append(e.getCorrespondOrgId()).append("', '")
                    .append(e.getOwnerTypeIdHead()).append("', '")
                    .append(e.getOwnerIdHeadName()).append("', '")
                    .append(e.getCreatorIdName()).append("', '")
                    .append(e.getCreateDate()).append("', '")
                    .append(e.getModifierIdName()).append("', '")
                    .append(e.getModifyDate()).append("', '")
                    .append(e.getApproverIdName()).append("', '")
                    .append(e.getApproveDate()).append("', '")
                    .append(e.getCancelStatus()).append("', '")
                    .append(e.getCancellerIdName()).append("', '")
                    .append(e.getCancelDate()).append("', '")
                    .append(e.getIsTotalServiceOrCost()).append("', '")
                    .append(e.getFISGENFORIOS()).append("', '")
                    .append(e.getSALReturnstockEntries()).append("', '")
                    .append(e.getResultRemark()).append("'");
            if (hasPartition) {
                builder.append(",'" + formatter.format(startDate) + "'");
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
