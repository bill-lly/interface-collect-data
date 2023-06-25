package com.robot.gs.kingdee.converter;


import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.PURPurchaseOrder;
import com.robot.gs.ticket.http.kingdee.result.PURPurchaseOrderFiles.POOrderEntry;
import com.robot.gs.ticket.http.kingdee.result.SALOutstockFiles.SALOutstockEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PURPurchaseOrderConverter extends KingdeeDataConverter<PURPurchaseOrder> {
    public PURPurchaseOrder from(String data) {
        PURPurchaseOrder purPurchaseOrder = new PURPurchaseOrder();
        try {
            map(purPurchaseOrder, data);
        } catch (RuntimeException e) {
            log.error("获取" + PURPurchaseOrder.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return purPurchaseOrder;
    }

    private void map(PURPurchaseOrder purPurchaseOrder, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        purPurchaseOrder.setId(jsonObject.getLong("Id"));
        //单据类型
        purPurchaseOrder.setBillTypeIdName(value(getListValue("BillTypeId", "Name", jsonObject)));
        //业务类型
        purPurchaseOrder.setBusinessType(value(jsonObject.getString("BusinessType")));
        //单据编号
        purPurchaseOrder.setBillNo(value(jsonObject.getString("BillNo")));
        //采购日期
        purPurchaseOrder.setDate(value(jsonObject.getString("Date")));
        //供应商
        purPurchaseOrder.setSupplierIdName(value(getListValue("SupplierId", "Name", jsonObject)));
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
        purPurchaseOrder.setDocumentStatus(value(documentStatus));
        //采购组织
        purPurchaseOrder.setPurchaseOrgIdName(value(getListValue("PurchaseOrgId", "Name", jsonObject)));
        //采购员
        purPurchaseOrder.setPurchaserIdName(value(getListValue("PurchaserId", "Name", jsonObject)));
        //手工关闭
        purPurchaseOrder.setManualClose(jsonObject.getString("MANUALCLOSE"));
        //创建人
        purPurchaseOrder.setCreatorIdName(value(getJsonValue("CreatorId", "Name", jsonObject)));
        //创建日期
        purPurchaseOrder.setCreateDate(value(jsonObject.getString("CreateDate")));
        //最后修改日期
        purPurchaseOrder.setModifyDate(value(jsonObject.getString("ModifyDate")));
        //审核日期
        purPurchaseOrder.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //关闭状态
        String closeStatus = "";
        if (jsonObject.getString("CloseStatus") != null) {
            switch (jsonObject.getString("CloseStatus")) {
                case "A":
                    closeStatus = "未关闭";
                    break;
                case "B":
                    closeStatus = "已关闭";
                    break;
                default:
                    closeStatus = null;
                    break;
            }
        }
        purPurchaseOrder.setCloseStatus(value(closeStatus));
        //关闭日期
        purPurchaseOrder.setCloseDate(value(jsonObject.getString("CloseDate")));
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
        purPurchaseOrder.setCancelStatus(value(cancelStatus));
        //作废日期
        purPurchaseOrder.setCancelDate(value(jsonObject.getString("CancelDate")));
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
        purPurchaseOrder.setConfirmStatus(value(confirmStatus));
        //确认日期
        purPurchaseOrder.setConfirmDate(value(jsonObject.getString("ConfirmDate")));
        //MRP运算号
        purPurchaseOrder.setFMRPNo(value(jsonObject.getString("F_MRPNo")));
        //采购申请号
        purPurchaseOrder.setFGsPr(value(jsonObject.getString("F_gs_pr")));
        //收货地址
        purPurchaseOrder.setFOraCombo(value(jsonObject.getString("F_ora_Combo")));
        //Entrys
        List<JSONObject> poOrderEntrys = jsonObject.getList("POOrderEntry", JSONObject.class) != null ? jsonObject.getList("POOrderEntry", JSONObject.class) : new ArrayList<>();
        List<POOrderEntry> poOrderEntrylist = new ArrayList<>();
        POOrderEntry orderEntry = null;
        for (JSONObject poOrderEntry : poOrderEntrys) {
            orderEntry = new POOrderEntry();
            getPOOderEntry(orderEntry, poOrderEntry);
            poOrderEntrylist.add(orderEntry);
        }
        String gsonStrCl1 = gson.toJson(poOrderEntrylist);
        purPurchaseOrder.setPOOrderEntry(value(gsonStrCl1));
        //是否正常获取数据备注
        purPurchaseOrder.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void getPOOderEntry(POOrderEntry orderEntry, JSONObject poOrderEntry) {
        if (poOrderEntry == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject materialId = (JSONObject) poOrderEntry.get("MaterialId");
        //EntryId
        orderEntry.setEntryId(poOrderEntry.getLong("Id"));
        //物料编码
        orderEntry.setMaterialIdNumber(value(materialId != null ? materialId.getString("Number") : null));
        //物料名称
        orderEntry.setMaterialIdName(value(getListValue("MaterialId", "Name", poOrderEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //物料类别
        orderEntry.setMaterialGroupName(value(getListValue("MaterialGroup", "Name", materialId)));
        //物料说明
        orderEntry.setMaterialDesc(value(getSimListValue("MaterialDesc", poOrderEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //规格型号
        orderEntry.setFoldModel(value(poOrderEntry.getString("FoldModel")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //辅助属性
        orderEntry.setMaterialAuxPty(value(materialId != null ? materialId.getString("MaterialAuxPty") : null));
        //采购单位
        orderEntry.setUnitIdName(value(getListValue("UnitId", "Name", poOrderEntry)));
        //采购数量
        orderEntry.setQty(value(poOrderEntry.getDouble("Qty"), 0.0));
        //交货日期
        orderEntry.setDeliveryDate(value(poOrderEntry.getString("DeliveryDate")));
        //金额
        orderEntry.setAmount(value(poOrderEntry.getDouble("Amount"), 0.0));
        //需求组织
        orderEntry.setRequireOrgIdName(value(getListValue("RequireOrgId", "Name", poOrderEntry)));
        //费用项目
        orderEntry.setChargeProjectID(value(getListValue("ChargeProjectID", "Name", poOrderEntry)));
        //需求人
        orderEntry.setRequireStaffIdName(value(getListValue("RequireStaffId", "Name", poOrderEntry)));
        //收料组织
        orderEntry.setReceiveOrgIdName(value(getListValue("ReceiveOrgId", "Name", poOrderEntry)));
        //备注
        orderEntry.setNote(value(poOrderEntry.getString("Note")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //最早交货日期
        orderEntry.setDeliveryEarlyDate(value(poOrderEntry.getString("DeliveryEarlyDate")));
        //最晚交货日期
        orderEntry.setDeliveryLastDate(value(poOrderEntry.getString("DeliveryLastDate")));
        //冻结日期
        orderEntry.setFreezeDate(value(poOrderEntry.getString("FreezeDate")));
        //终止日期
        orderEntry.setTerminateDate(value(poOrderEntry.getString("TerminateDate")));
        //累计收料数量
        orderEntry.setReceiveQty(value(poOrderEntry.getDouble("ReceiveQty"), 0.0));
        //剩余收料数量
        orderEntry.setRemainReceiveQty(value(poOrderEntry.getDouble("RemainReceiveQty"), 0.0));
        //累计入库数量
        orderEntry.setStockInQty(value(poOrderEntry.getDouble("StockInQty"), 0.0));
        //剩余入库数量
        orderEntry.setRemainStockInQty(value(poOrderEntry.getDouble("RemainStockInQty"), 0.0));
        //累计退料数量
        orderEntry.setMrbQty(value(poOrderEntry.getDouble("MrbQty"), 0.0));
        //源单类型
        orderEntry.setSrcBillTypeId(value(poOrderEntry.getString("SrcBillTypeId")));
        //源单编号
        orderEntry.setSrcBillNo(value(poOrderEntry.getString("SrcBillNo")));
        //需求来源
        orderEntry.setDemandType(value(poOrderEntry.getString("DEMANDTYPE")));
        //需求单据编号
        orderEntry.setDemandBillNo(value(poOrderEntry.getString("DEMANDBILLNO")));
        //需求单据行号
        orderEntry.setDemandBillEntrySeq(poOrderEntry.getInteger("DEMANDBILLENTRYSEQ"));
        //计划确认
        orderEntry.setPlanConfirm(value(poOrderEntry.getString("PlanConfirm")));
        //研发项目
        orderEntry.setFResearchProject(value(getListValue("F_ResearchProject", "FDataValue", poOrderEntry)));
        //采购申请行号
        orderEntry.setFGsPrItem(value(poOrderEntry.getString("F_gs_pr_item")));
    }


    @Override
    public PURPurchaseOrder from(Map<String, Object> data) {
        return null;
    }
}
