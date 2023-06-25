package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.STKInstock;
import com.robot.gs.ticket.http.kingdee.result.STKInstockFiles.InStockEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class STKInstockConverter extends KingdeeDataConverter<STKInstock> {
    public STKInstock from(String data) {
        STKInstock STKInstock = new STKInstock();
        try {
            map(STKInstock, data);
        } catch (RuntimeException e) {
            log.error("获取" + STKInstock.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return STKInstock;
    }

    private void map(STKInstock STKInstock, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        STKInstock.setId(jsonObject.getLong("Id"));
        //单据类型
        STKInstock.setFBillTypeIDName(value(getListValue("FBillTypeID", "Name", jsonObject)));
        //应付状态
        String apStatus = "";
        if (jsonObject.getString("APSTATUS") != null) {
            switch (jsonObject.getString("APSTATUS")) {
                case "Y":
                    apStatus = "已应付";
                    break;
                case "N":
                    apStatus = "未应付";
                    break;
                default:
                    apStatus = null;
                    break;
            }
        }
        STKInstock.setAPStatus(value(apStatus));
        //创建人
        STKInstock.setCreatorIdName(value(getJsonValue("CreatorId", "Name", jsonObject)));
        //供货方
        STKInstock.setSupplyIdName(value(getListValue("SupplyId", "Name", jsonObject)));
        //创建日期
        STKInstock.setCreateDate(value(jsonObject.getString("CreateDate")));
        //结算方
        STKInstock.setSettleIdName(value(getListValue("SettleId", "Name", jsonObject)));
        //业务类型
        STKInstock.setBusinessType(value(jsonObject.getString("BusinessType")));
        //单据编号
        STKInstock.setBillNo(value(jsonObject.getString("BillNo")));
        //最后修改人
        STKInstock.setFModifierIdName(value(getJsonValue("FModifierId", "Name", jsonObject)));
        //收款方
        STKInstock.setChargeIdName(value(getListValue("ChargeId", "Name", jsonObject)));
        //入库日期
        STKInstock.setDate(value(jsonObject.getString("Date")));
        //最后修改日期
        STKInstock.setFModifyDate(value(jsonObject.getString("FModifyDate")));
        //供应商
        STKInstock.setSupplierIdName(value(getListValue("SupplierId", "Name", jsonObject)));
        //审核人
        STKInstock.setApproverIdName(value(getJsonValue("ApproverId", "Name", jsonObject)));
        //收料组织
        STKInstock.setStockOrgIdName(value(getListValue("StockOrgId", "Name", jsonObject)));
        //货主
        STKInstock.setOwnerIdHeadName(value(getListValue("OwnerIdHead", "Name", jsonObject)));
        //审核日期
        STKInstock.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //收料部门
        STKInstock.setStockDeptIdName(value(getListValue("StockDeptId", "Name", jsonObject)));
        //作废人
        STKInstock.setCancellerIdName(value(getJsonValue("CancellerId", "Name", jsonObject)));
        //货主类型
        STKInstock.setOwnerTypeIdHead(value(jsonObject.getString("OwnerTypeIdHead")));
        //库存组
        STKInstock.setStockerGroupIdName(value(getListValue("StockerGroupId", "Name", jsonObject)));
        //作废日期
        STKInstock.setCancelDate(value(jsonObject.getString("CancelDate")));
        //仓管员
        STKInstock.setStockerIdName(value(getListValue("StockerId", "Name", jsonObject)));
        //作废状态
        String cancelStatus = "";
        if (jsonObject.getString("CancelStatus") != null) {
            switch (jsonObject.getString("CancelStatus")) {
                case "A":
                    cancelStatus = "否";
                    break;
                case "B":
                    cancelStatus = "是";
                    break;
                default:
                    cancelStatus = null;
                    break;
            }
        }
        STKInstock.setCancelStatus(value(cancelStatus));
        //采购组织
        STKInstock.setPurchaseOrgIdName(value(getListValue("PurchaseOrgId", "Name", jsonObject)));
        //单据状态
        String documentStatus = "";
        if (jsonObject.getString("DocumentStatus") != null) {
            switch (jsonObject.getString("DocumentStatus")) {
                case "Z":
                    documentStatus = "暂存";
                    break;
                case "A":
                    documentStatus = "创建";
                    break;
                case "B":
                    documentStatus = "审核中";
                    break;
                case "C":
                    documentStatus = "已审核";
                    break;
                case "D":
                    documentStatus = "重新审核";
                    break;
                default:
                    documentStatus = null;
                    break;
            }
        }
        STKInstock.setDocumentStatus(value(documentStatus));
        //采购部门
        STKInstock.setPurchaseDeptIdName(value(getListValue("PurchaseDeptId", "Name", jsonObject)));
        //需求组织
        STKInstock.setDemandOrgIdName(value(getListValue("DemandOrgId", "Name", jsonObject)));
        //采购员
        STKInstock.setPurchaserIdName(value(getListValue("PurchaserId", "Name", jsonObject)));
        //组织间结算跨法人标识
        STKInstock.setIsInterLegalPerson(value(jsonObject.getString("IsInterLegalPerson")));
        //确认日期
        STKInstock.setConfirmDate(value(jsonObject.getString("ConfirmDate")));
        //确认状态
        String confirmStatus = "";
        if (jsonObject.getString("ConfirmStatus") != null) {
            switch (jsonObject.getString("ConfirmStatus")) {
                case "A":
                    confirmStatus = "未确认";
                    break;
                case "B":
                    confirmStatus = "已确认";
                    break;
                default:
                    confirmStatus = null;
                    break;
            }
        }
        STKInstock.setConfirmStatus(value(confirmStatus));
        //拆单类型
        String splitBillType = "";
        if (jsonObject.getString("SplitBillType") != null) {
            switch (jsonObject.getString("SplitBillType")) {
                case "A":
                    splitBillType = "正常";
                    break;
                case "B":
                    splitBillType = "母单";
                    break;
                case "C":
                    splitBillType = "子单";
                    break;
                default:
                    splitBillType = null;
                    break;
            }
        }
        STKInstock.setSplitBillType(value(splitBillType));
        //Entries(InStockEntry)
        List<JSONObject> InStockEntries = jsonObject.getList("InStockEntry", JSONObject.class) != null ? jsonObject.getList("InStockEntry", JSONObject.class) : new ArrayList<>();
        List<InStockEntry> inStockEntryLists = new ArrayList<>();
        InStockEntry inStockEntry = null;
        for (JSONObject inStockEntryJson : InStockEntries) {
            inStockEntry = new InStockEntry();
            setEntries(inStockEntry, inStockEntryJson);
            inStockEntryLists.add(inStockEntry);
        }
        String gsonStrCl1 = gson.toJson(inStockEntryLists);
        STKInstock.setInStockEntry(value(gsonStrCl1));
        //是否正常获取数据备注
        STKInstock.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void setEntries(InStockEntry inStockEntry, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //EntryId(InStockEntry)
        inStockEntry.setEntryId(jsonObject.getLong("Id"));
        //物料编码
        inStockEntry.setMaterialIdNumber(value(getJsonValue("MaterialId", "Number", jsonObject)));
        //物料名称
        inStockEntry.setMaterialIdName(value(getListValue("MaterialId", "Name", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //规格型号
        inStockEntry.setMaterialIdSpec(value(getListValue("MaterialId", "Specification", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //库存单位
        inStockEntry.setUnitIDName(value(getListValue("UnitID", "Name", jsonObject)));
        //应收数量
        inStockEntry.setMustQty(value(jsonObject.getDouble("MustQty"), 0.0));
        //实收数量
        inStockEntry.setRealQty(value(jsonObject.getDouble("RealQty"), 0.0));
        //成本价
        inStockEntry.setCostPrice(value(jsonObject.getDouble("CostPrice"), 0.0));
        //仓库
        inStockEntry.setStockIdName(value(getListValue("StockId", "Name", jsonObject)));
        //含税单价
        inStockEntry.setTaxPrice(value(jsonObject.getDouble("TaxPrice"), 0.0));
        //仓位
        JSONObject stockLocId = jsonObject.getJSONObject("StockLocId");
        inStockEntry.setStockLocIdSp(value(getListValue("F100003", "Name", stockLocId)));
        //库存状态
        inStockEntry.setStockStatusIdName(value(getListValue("StockStatusId", "Name", jsonObject)));
        //源单类型
        inStockEntry.setSrcBillTypeId(value(jsonObject.getString("SRCBILLTYPEID")));
        //源单编号
        inStockEntry.setSRCBillNo(value(jsonObject.getString("SRCBillNo")));
        //关联应付数量（计价基本）
        inStockEntry.setBaseAPJoinQty(value(jsonObject.getDouble("BaseAPJoinQty"), 0.0));
        //已开票数量
        inStockEntry.setInvoicedQty(value(jsonObject.getDouble("InvoicedQty"), 0.0));
        //货主类型.1
        inStockEntry.setOwnerTypeId(value(jsonObject.getString("OWNERTYPEID")));
        //货主.1
        inStockEntry.setOwnerIdName(value(getListValue("OWNERID", "Name", jsonObject)));
        //税率(%)
        inStockEntry.setTaxRate(value(jsonObject.getDouble("TaxRate"), 0.0));
        //基本单位
        inStockEntry.setBaseUnitIDName(value(getListValue("BaseUnitID", "Name", jsonObject)));
        //关联数量(基本单位)
        inStockEntry.setBaseJoinQty(value(jsonObject.getDouble("BaseJoinQty"), 0.0));
        //订单单号
        inStockEntry.setPOOrderNo(value(jsonObject.getString("POOrderNo")));
        //税额
        inStockEntry.setTaxAmount(value(jsonObject.getDouble("TaxAmount"), 0.0));
        //库存基本数量
        inStockEntry.setBaseUnitQty(value(jsonObject.getDouble("BaseUnitQty"), 0.0));
        //材料成本
        inStockEntry.setMaterialCosts(value(jsonObject.getDouble("MaterialCosts"), 0.0));
        //材料成本(本位币)
        inStockEntry.setMaterialCostsLC(value(jsonObject.getDouble("MaterialCosts_LC"), 0.0));
        //单价
        inStockEntry.setPrice(value(jsonObject.getDouble("Price"), 0.0));
        //系统定价
        inStockEntry.setSysPrice(value(jsonObject.getDouble("SysPrice"), 0.0));
        //税额(本位币)
        inStockEntry.setTaxAmountLC(value(jsonObject.getDouble("TaxAmount_LC"), 0.0));
        //金额（本位币）
        inStockEntry.setAmountLC(value(jsonObject.getDouble("Amount_LC"), 0.0));
        //价格系数
        inStockEntry.setPriceCoefficient(value(jsonObject.getDouble("PriceCoefficient"), 0.0));
        //折扣额
        inStockEntry.setDiscount(value(jsonObject.getDouble("Discount"), 0.0));
        //退料关联数量
        inStockEntry.setReturnJoinQty(value(jsonObject.getDouble("ReturnJoinQty"), 0.0));
        //计划跟踪号
        inStockEntry.setMtoNo(value(jsonObject.getString("MtoNo")));
        //供应商批号
        inStockEntry.setSupplierLot(value(jsonObject.getString("SupplierLot")));
        //计价数量
        inStockEntry.setPriceUnitQty(value(jsonObject.getDouble("PriceUnitQty"), 0.0));
        //数量（库存辅单位）
        inStockEntry.setAuxUnitQty(value(jsonObject.getDouble("AuxUnitQty"), 0.0));
        //产品类型
        inStockEntry.setRowType(value(jsonObject.getString("RowType")));
        //净价
        inStockEntry.setFTaxNetPrice(value(jsonObject.getDouble("FTaxNetPrice"), 0.0));
        //父行标识
        inStockEntry.setParentRowId(value(jsonObject.getString("ParentRowId")));
        //总成本
        inStockEntry.setBillCostAmount(value(jsonObject.getDouble("BillCostAmount"), 0.0));
        //总成本(本位币)
        inStockEntry.setBillCostAmountLC(value(jsonObject.getDouble("BillCostAmount_LC"), 0.0));
        //价税合计
        inStockEntry.setAllAmount(value(jsonObject.getDouble("AllAmount"), 0.0));
        //价税合计(本位币)
        inStockEntry.setAllAmountLC(value(jsonObject.getDouble("AllAmount_LC"), 0.0));
        //已开票关联数量
        inStockEntry.setInvoicedJoinQty(value(jsonObject.getDouble("InvoicedJoinQty"), 0.0));
        //入库类型
        inStockEntry.setWWInType(value(jsonObject.getString("WWInType")));
        //计价基本数量
        inStockEntry.setPriceBaseQty(value(jsonObject.getDouble("PriceBaseQty"), 0.0));
        //定价单位
        inStockEntry.setSetPriceUnitID(value(jsonObject.getString("SetPriceUnitID")));
        //采购单位
        inStockEntry.setRemainInStockUnitIdName(value(getListValue("RemainInStockUnitId", "Name", jsonObject)));
        //采购数量
        inStockEntry.setRemainInStockQty(value(jsonObject.getDouble("RemainInStockQty"), 0.0));
        //退料关联数量(采购基本)
        inStockEntry.setRETURNSTOCKJNBASEQTY(value(jsonObject.getDouble("RETURNSTOCKJNBASEQTY"), 0.0));
        //关联应付数量（库存基本)
        inStockEntry.setStockBaseAPJoinQty(value(jsonObject.getDouble("StockBaseAPJoinQty"), 0.0));
        //成本价(本位币)
        inStockEntry.setCOSTPRICE_LC(value(jsonObject.getDouble("COSTPRICE_LC"), 0.0));
        //未关联应付数量（计价单位）
        inStockEntry.setAPNotJoinQty(value(jsonObject.getDouble("APNotJoinQty"), 0.0));
        //关联应付金额
        inStockEntry.setAPJoinAmount(value(jsonObject.getDouble("APJoinAmount"), 0.0));
        //应付关闭状态
        String payableCloseStatus = "";
        if (jsonObject.getString("PayableCloseStatus") != null) {
            switch (jsonObject.getString("PayableCloseStatus")) {
                case "A":
                    payableCloseStatus = "未关闭";
                    break;
                case "B":
                    payableCloseStatus = "已关闭";
                    break;
                default:
                    payableCloseStatus = null;
                    break;
            }
        }
        inStockEntry.setPayableCloseStatus(value(payableCloseStatus));
        //应付关闭日期
        inStockEntry.setPayableCloseDate(value(jsonObject.getString("PayableCloseDate")));
        //研发项目
        inStockEntry.setFResearchProject(value(getListValue("FResearchProject", "FDataValue", jsonObject)));
        //物料说明
        inStockEntry.setMaterialDesc(value(getSimListValue("MaterialDesc", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //对账中
        inStockEntry.setIsReconciliationing(value(jsonObject.getString("IsReconciliationing")));
    }

    @Override
    public STKInstock from(Map<String, Object> data) {
        return null;
    }
}
