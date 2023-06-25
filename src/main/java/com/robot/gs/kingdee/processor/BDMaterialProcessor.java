package com.robot.gs.kingdee.processor;

import com.robot.gs.common.ApiNameEnum;
import com.robot.gs.common.CommonDataConverter;
import com.robot.gs.kingdee.bean.BDMaterial;
import com.robot.gs.kingdee.converter.BDMaterialConverter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
public class BDMaterialProcessor extends KingdeeCommonDataProcessor {

    private final BDMaterialConverter converter = new BDMaterialConverter();

    public BDMaterialProcessor(ApiNameEnum apiNameEnum, String dbName, String tbName, LocalDate startDate,
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
            BDMaterial e = converter.from(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            builder.append("STRUCT ('")
                    .append(e.getId()).append("', '")
                    .append(e.getNumber()).append("', '")
                    .append(e.getName()).append("', '")
                    .append(e.getCreateOrgIdName()).append("', '")
                    .append(e.getFOraInt()).append("', '")
                    .append(e.getUseOrgIdName()).append("', '")
                    .append(e.getMaterialSRC()).append("', '")
                    .append(e.getSpecification()).append("', '")
                    .append(e.getMnemonicCode()).append("', '")
                    .append(e.getOldNumber()).append("', '")
                    .append(e.getMaterialGroupName()).append("', '")
                    .append(e.getMaterialGroupNumber()).append("', '")
                    .append(e.getDocumentStatus()).append("', '")
                    .append(e.getForbidStatus()).append("', '")
                    .append(e.getCreatorIdName()).append("', '")
                    .append(e.getCreateDate()).append("', '")
                    .append(e.getModifierIdName()).append("', '")
                    .append(e.getModifyDate()).append("', '")
                    .append(e.getApproverIdName()).append("', '")
                    .append(e.getApproveDate()).append("', '")
                    .append(e.getForbidderId()).append("', '")
                    .append(e.getForbidDate()).append("', '")
                    .append(e.getImgStorageType()).append("', '")
                    .append(e.getImage()).append("', '")
                    .append(e.getDSMatchByLot()).append("', '")
                    .append(e.getImageFileServer()).append("', '")
                    .append(e.getForbidReason()).append("', '")
                    .append(e.getRefStatus()).append("', '")
                    .append(e.getFOraText()).append("', '")
                    .append(e.getFOraText1()).append("', '")
                    .append(e.getFOraCombo()).append("', '")
                    .append(e.getFPATCCombo()).append("', '")
                    .append(e.getEntryIdMB()).append("', '")
                    .append(e.getErpClsID()).append("', '")
                    .append(e.getCategoryIDName()).append("', '")
                    .append(e.getTaxType()).append("', '")
                    .append(e.getTaxRateIdName()).append("', '")
                    .append(e.getBaseUnitIdName()).append("', '")
                    .append(e.getIsPurchase()).append("', '")
                    .append(e.getIsInventory()).append("', '")
                    .append(e.getIsSubContract()).append("', '")
                    .append(e.getIsSale()).append("', '")
                    .append(e.getIsProduce()).append("', '")
                    .append(e.getIsAsset()).append("', '")
                    .append(e.getBarCode()).append("', '")
                    .append(e.getGrossWeight()).append("', '")
                    .append(e.getNetWeight()).append("', '")
                    .append(e.getVolumeUnitIdName()).append("', '")
                    .append(e.getLength()).append("', '")
                    .append(e.getWidth()).append("', '")
                    .append(e.getHeight()).append("', '")
                    .append(e.getWeightUnitIdName()).append("', '")
                    .append(e.getVolume()).append("', '")
                    .append(e.getConfigType()).append("', '")
                    .append(e.getFeatureItem()).append("', '")
                    .append(e.getSuite()).append("', '")
                    .append(e.getCostPriceRate()).append("', '")
                    .append(e.getIsChange()).append("', '")
                    .append(e.getEntryIdMS()).append("', '")
                    .append(e.getStoreUnitIDName()).append("', '")
                    .append(e.getAuxUnitID()).append("', '")
                    .append(e.getStockId()).append("', '")
                    .append(e.getStockPlaceId()).append("', '")
                    .append(e.getSpProdMtrlNumber()).append("', '")
                    .append(e.getSpProdMtrlName()).append("', '")
                    .append(e.getSpSalMtrlNumber()).append("', '")
                    .append(e.getSpSalMtrlName()).append("', '")
                    .append(e.getSpPttRentNumber()).append("', '")
                    .append(e.getSpPttRentName()).append("', '")
                    .append(e.getSpZjgNpftNumber()).append("', '")
                    .append(e.getSpZjgNpftName()).append("', '")
                    .append(e.getSpRTVirtualNumber()).append("', '")
                    .append(e.getSpRTVirtualName()).append("', '")
                    .append(e.getSpCtrlBoxNumber()).append("', '")
                    .append(e.getSpCtrlBoxName()).append("', '")
                    .append(e.getSpZjgRsMtrlNumber()).append("', '")
                    .append(e.getSpZjgRsMtrlName()).append("', '")
                    .append(e.getSpToolsNumber()).append("', '")
                    .append(e.getSpToolsName()).append("', '")
                    .append(e.getSpZjgQltTestNumber()).append("', '")
                    .append(e.getSpZjgQltTestName()).append("', '")
                    .append(e.getSpParkNumber()).append("', '")
                    .append(e.getSpParkName()).append("', '")
                    .append(e.getIsLockStock()).append("', '")
                    .append(e.getIsCycleCounting()).append("', '")
                    .append(e.getCountCycle()).append("', '")
                    .append(e.getCountDay()).append("', '")
                    .append(e.getIsMustCounting()).append("', '")
                    .append(e.getIsBatchManage()).append("', '")
                    .append(e.getBatchRuleID()).append("', '")
                    .append(e.getIsKFPeriod()).append("', '")
                    .append(e.getIsExpParToFlot()).append("', '")
                    .append(e.getExpUnit()).append("', '")
                    .append(e.getExpPeriod()).append("', '")
                    .append(e.getOnlineLife()).append("', '")
                    .append(e.getRefCost()).append("', '")
                    .append(e.getCurrencyIdName()).append("', '")
                    .append(e.getIsSNManage()).append("', '")
                    .append(e.getIsEnableMinStock()).append("', '")
                    .append(e.getIsEnableSafeStock()).append("', '")
                    .append(e.getSNCodeRule()).append("', '")
                    .append(e.getIsEnableReOrder()).append("', '")
                    .append(e.getIsEnableMaxStock()).append("', '")
                    .append(e.getSNUnit()).append("', '")
                    .append(e.getMinStock()).append("', '")
                    .append(e.getSafeStock()).append("', '")
                    .append(e.getReOrderGood()).append("', '")
                    .append(e.getEconReOrderQty()).append("', '")
                    .append(e.getMaxStock()).append("', '")
                    .append(e.getIsSNPRDTracy()).append("', '")
                    .append(e.getBoxStandardQty()).append("', '")
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
