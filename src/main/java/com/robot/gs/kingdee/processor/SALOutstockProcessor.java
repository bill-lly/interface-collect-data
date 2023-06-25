package com.robot.gs.kingdee.processor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.kingdee.bean.SALOutstock;
import com.robot.gs.kingdee.converter.SALOutstockConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
public class SALOutstockProcessor extends KingdeeCommonDataProcessor {

    private final SALOutstockConverter converter = new SALOutstockConverter();

    public SALOutstockProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
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
            SALOutstock e = converter.from(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getId()).append("', '")
                    .append(e.getBillNo()).append("', '")
                    .append(e.getFDate()).append("', '")
                    .append(e.getCustomerIDName()).append("', '")
                    .append(e.getStockOrgIdName()).append("', '")
                    .append(e.getDocumentStatus()).append("', '")
                    .append(e.getBillTypeIDName()).append("', '")
                    .append(e.getSaleOrgIdName()).append("', '")
                    .append(e.getSaleDeptIDName()).append("', '")
                    .append(e.getSalesManIDName()).append("', '")
                    .append(e.getBussinessType()).append("', '")
                    .append(e.getReceiverIDName()).append("', '")
                    .append(e.getSettleIDName()).append("', '")
                    .append(e.getPayerIDName()).append("', '")
                    .append(e.getTransferBizType()).append("', '")
                    .append(e.getFCreatorIdName()).append("', '")
                    .append(e.getFCreateDate()).append("', '")
                    .append(e.getFModifyDate()).append("', '")
                    .append(e.getApproverIDName()).append("', '")
                    .append(e.getFModifierIdName()).append("', '")
                    .append(e.getApproveDate()).append("', '")
                    .append(e.getCancelStatus()).append("', '")
                    .append(e.getCancelDate()).append("', '")
                    .append(e.getOwnerIdHeadName()).append("', '")
                    .append(e.getFSaleContractNo()).append("', '")
                    .append(e.getFIncomeType()).append("', '")
                    .append(e.getFReceiveAddress()).append("', '")
                    .append(e.getFGSSalesReigon()).append("', '")
                    .append(e.getFGsSo()).append("', '")
                    .append(e.getFISGENFORIOS()).append("', '")
                    .append(e.getSALOutstockEntries()).append("', '")
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
