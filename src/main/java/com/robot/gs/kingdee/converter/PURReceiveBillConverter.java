package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.PURReceiveBill;
import com.robot.gs.ticket.http.kingdee.result.PURPurchaseOrderFiles.POOrderEntry;
import com.robot.gs.ticket.http.kingdee.result.PURReceiveBillFiles.PURReceiveEntry;
import com.robot.gs.ticket.http.kingdee.result.STKInstockFiles.InStockEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PURReceiveBillConverter extends KingdeeDataConverter<PURReceiveBill> {
    public PURReceiveBill from(String data) {
        PURReceiveBill PURReceiveBill = new PURReceiveBill();
        try {
            map(PURReceiveBill, data);
        } catch (RuntimeException e) {
            log.error("获取" + PURReceiveBill.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return PURReceiveBill;
    }

    private void map(PURReceiveBill PURReceiveBill, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        PURReceiveBill.setId(jsonObject.getLong("Id"));
        //单据类型
        PURReceiveBill.setBillTypeID(value(getListValue("BillTypeId", "Name", jsonObject)));
        //创建人
        PURReceiveBill.setCreatorIdName(value(getJsonValue("CreatorId", "Name", jsonObject)));
        //供货方
        PURReceiveBill.setSupplyIdName(value(getListValue("SupplyId", "Name", jsonObject)));
        //创建日期
        PURReceiveBill.setCreateDate(value(jsonObject.getString("CreateDate")));
        //结算方
        PURReceiveBill.setSFSettleIdName(value(getListValue("SettleId", "Name", jsonObject)));
        //业务类型
        PURReceiveBill.setBusinessType(value(jsonObject.getString("BusinessType")));
        //单据编号
        PURReceiveBill.setBillNo(value(jsonObject.getString("BillNo")));
        //最后修改人
        PURReceiveBill.setModifierIdName(value(getJsonValue("ModifierId", "Name", jsonObject)));
        //收款方
        PURReceiveBill.setSFChargeIdName(value(getListValue("ChargeId", "Name", jsonObject)));
        //收料日期
        PURReceiveBill.setDate(value(jsonObject.getString("Date")));
        //最后修改日期
        PURReceiveBill.setModifyDate(value(jsonObject.getString("ModifyDate")));
        //供应商
        PURReceiveBill.setSupplierIdName(value(getListValue("SupplierId", "Name", jsonObject)));
        //审核人
        PURReceiveBill.setApproverIdName(value(getJsonValue("ApproverId", "Name", jsonObject)));
        //收料组织
        PURReceiveBill.setStockOrgIdName(value(getListValue("StockOrgId", "Name", jsonObject)));
        //审核日期
        PURReceiveBill.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //收料部门
        PURReceiveBill.setReceiveDeptIdName(value(getListValue("ReceiveDeptId", "Name", jsonObject)));
        //作废人
        PURReceiveBill.setCancellerId(value(getJsonValue("CancellerId", "Name", jsonObject)));
        //作废日期
        PURReceiveBill.setCancelDate(value(jsonObject.getString("CancelDate")));
        //收料员
        PURReceiveBill.setReceiverIdName(value(getListValue("ReceiverId", "Name", jsonObject)));
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
        PURReceiveBill.setCancelStatus(value(cancelStatus));
        //采购组织
        PURReceiveBill.setPurOrgIdName(value(getListValue("PurOrgId", "Name", jsonObject)));
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
        PURReceiveBill.setDocumentStatus(value(documentStatus));
        //货主类型
        PURReceiveBill.setOwnerTypeIdHead(value(jsonObject.getString("OwnerTypeIdHead")));
        //货主
        PURReceiveBill.setOwnerIdHeadName(value(getListValue("OwnerIdHead", "Name", jsonObject)));
        //采购部门
        PURReceiveBill.setPurDeptIdName(value(getListValue("PurDeptId", "Name", jsonObject)));
        //需求组织
        PURReceiveBill.setDemandOrgIdName(value(getListValue("DemandOrgId", "Name", jsonObject)));
        //采购组
        PURReceiveBill.setPurGroupId(value(getListValue("PurGroupId", "Name", jsonObject)));
        //采购员
        PURReceiveBill.setPurchaserIdName(value(getListValue("PurchaserId", "Name", jsonObject)));
        //备注
        PURReceiveBill.setNote(value(jsonObject.getString("Note")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
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
        PURReceiveBill.setConfirmStatus(value(confirmStatus));
        //验收方式
        String accType = "";
        if (jsonObject.getString("ACCTYPE") != null) {
            switch (jsonObject.getString("ACCTYPE")) {
                case "A":
                    accType = "金额验收";
                    break;
                case "Q":
                    accType = "数量验收";
                    break;
                case "R":
                    accType = "比例验收";
                    break;
                default:
                    accType = null;
                    break;
            }
        }
        PURReceiveBill.setAccType(value(accType));
        //Entries(PURReceiveEntry)
        List<JSONObject> purReceiveEntries = jsonObject.getList("PUR_ReceiveEntry", JSONObject.class) != null ? jsonObject.getList("PUR_ReceiveEntry", JSONObject.class) : new ArrayList<>();
        List<PURReceiveEntry> purReceiveEntryLists = new ArrayList<>();
        PURReceiveEntry receiveEntry = null;
        for (JSONObject purReceiveEntryJson : purReceiveEntries) {
            receiveEntry = new PURReceiveEntry();
            setEntries(receiveEntry, purReceiveEntryJson);
            purReceiveEntryLists.add(receiveEntry);
        }
        String gsonStrCl1 = gson.toJson(purReceiveEntryLists);
        PURReceiveBill.setPURReceiveEntry(value(gsonStrCl1));
        //是否正常获取数据备注
        PURReceiveBill.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void setEntries(PURReceiveEntry receiveEntry, JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //EntryId
        receiveEntry.setEntryId(jsonObject.getLong("Id"));
        //物料编码
        receiveEntry.setMaterialIDNumber(value(getJsonValue("MaterialID", "Number", jsonObject)));
        //物料名称
        receiveEntry.setMaterialIDName(value(getListValue("MaterialID", "Name", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //需求人
        receiveEntry.setDemanderIdName(value(getListValue("DemanderId", "Name", jsonObject)));
        //物料说明
        receiveEntry.setMaterialDesc(value(getSimListValue("MaterialDesc", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //规格型号
        receiveEntry.setSpecification(value(getListValue("MaterialID", "Specification", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //物料类别
        JSONObject materialID = jsonObject.getJSONObject("MaterialID");
        receiveEntry.setMaterialGroup(value(getListValue("MaterialGroup", "Name", materialID)));
        //控制入库数量
        receiveEntry.setCtrlStockInPercent(value(jsonObject.getString("CtrlStockInPercent")));
        //合格入库关联数量
        receiveEntry.setInStockJoinQty(value(jsonObject.getDouble("InStockJoinQty"), 0.0));
        //收料单位
        receiveEntry.setUnitIdName(value(getListValue("UnitId", "Name", jsonObject)));
        //入库数量
        receiveEntry.setInStockQty(value(jsonObject.getDouble("InStockQty"), 0.0));
        //实收数量
        receiveEntry.setMustQty(value(jsonObject.getDouble("MustQty"), 0.0));
        //预计到货日期
        receiveEntry.setPreDeliveryDate(value(jsonObject.getString("PreDeliveryDate")));
        //实到数量
        receiveEntry.setActLandQty(value(jsonObject.getDouble("ActlandQty"), 0.0));
        //采购基本数量
        receiveEntry.setBaseUnitQty(value(jsonObject.getDouble("BaseUnitQty"), 0.0));
        //交货数量
        receiveEntry.setActReceiveQty(value(jsonObject.getDouble("ActReceiveQty"), 0.0));
        //退料数量
        receiveEntry.setReturnQty(value(jsonObject.getDouble("ReturnQty"), 0.0));
        //拒收数量
        receiveEntry.setRejectQty(value(jsonObject.getDouble("RejectQty"), 0.0));
        //基本单位
        receiveEntry.setBaseUnitIdName(value(getListValue("BaseUnitId", "Name", jsonObject)));
        //仓库
        receiveEntry.setStockID(value(getListValue("StockID", "Name", jsonObject)));
        //源单内码
        receiveEntry.setSrcId(value(jsonObject.getString("SrcId")));
        //备注.1
        receiveEntry.setDescription(value(jsonObject.getString("Description")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //仓位
        JSONObject stockLocId = jsonObject.getJSONObject("StockLocId");
        receiveEntry.setStockLocId(value(getListValue("F100003", "Name", stockLocId)));
        //库存状态
        receiveEntry.setStockStatusIdName(value(getListValue("StockStatusId", "Name", jsonObject)));
        //源单类型
        receiveEntry.setSrcFormId(value(jsonObject.getString("SrcFormId")));
        //源单分录内码
        List<JSONObject> fDetailEntity_link = jsonObject.getList("FDetailEntity_Link", JSONObject.class) != null ? jsonObject.getList("FDetailEntity_Link", JSONObject.class) : new ArrayList<>();
        receiveEntry.setFDetailEntityLinkSId(value(fDetailEntity_link.size() > 0 ? (fDetailEntity_link.get(0) != null ? fDetailEntity_link.get(0).getString("SId") : null) : null));
        //源单单号
        receiveEntry.setSrcBillNo(value(jsonObject.getString("SrcBillNo")));
        //订单单号
        receiveEntry.setOrderBillNo(value(jsonObject.getString("OrderBillNo")));
        //计价数量
        receiveEntry.setPriceUnitQty(value(jsonObject.getDouble("PriceUnitQty"), 0.0));
        //含税单价
        receiveEntry.setTaxPrice(value(jsonObject.getDouble("TaxPrice"), 0.0));
        //是否赠品
        receiveEntry.setGiveAway(value(jsonObject.getString("GiveAway")));
        //费用项目
        receiveEntry.setChargeProjectID(value(getListValue("ChargeProjectID", "Name", jsonObject)));
        //价税合计(本位币)
        receiveEntry.setAllAmountLC(value(jsonObject.getDouble("AllAmount_LC"), 0.0));
        //紧急放行
        String emergencyRelease = "";
        if (jsonObject.getString("EmergencyRelease") != null) {
            switch (jsonObject.getString("EmergencyRelease")) {
                case "A":
                    emergencyRelease = "否";
                    break;
                case "B":
                    emergencyRelease = "是";
                    break;
                default:
                    emergencyRelease = null;
                    break;
            }
        }
        receiveEntry.setEmergencyRelease(value(emergencyRelease));
        //检验关联数量
        receiveEntry.setCheckJoinQty(value(jsonObject.getDouble("CheckJoinQty"), 0.0));
        //合格数量
        receiveEntry.setReceiveQty(value(jsonObject.getDouble("ReceiveQty"), 0.0));
        //判退数量
        receiveEntry.setRefuseQty(value(jsonObject.getDouble("RefuseQty"), 0.0));
        //样本破坏数量
        receiveEntry.setSampleDamageQty(value(jsonObject.getDouble("SampleDamageQty"), 0.0));
        //来料检验
        receiveEntry.setCheckIncoming(value(jsonObject.getString("CheckInComing")));
        //卡片关联数量
        receiveEntry.setCardJoinQty(value(jsonObject.getDouble("CardJoinQty"), 0.0));
        //卡片数量
        receiveEntry.setCardQty(value(jsonObject.getDouble("CardQty"), 0.0));
        //检验数量
        receiveEntry.setCheckQty(value(jsonObject.getDouble("CheckQty"), 0.0));
        //让步接收数量(基本单位)
        receiveEntry.setCsnReceiveBaseQty(value(jsonObject.getDouble("CsnReceiveBaseQty"), 0.0));
        //让步接收数量
        receiveEntry.setCsnReceiveQty(value(jsonObject.getDouble("CsnReceiveQty"), 0.0));
        //不合格关联数量(基本单位)
        receiveEntry.setRefuseJoinBaseQty(value(jsonObject.getDouble("RefuseJoinBaseQty"), 0.0));
        //让步接收关联数量(基本单位)
        receiveEntry.setCsnReceiveJoinBaseQty(value(jsonObject.getDouble("CsnReceiveJoinBaseQty"), 0.0));
        //计价基本数量
        receiveEntry.setPriceBaseQty(value(jsonObject.getDouble("PriceBaseQty"), 0.0));
        //定价单位
        receiveEntry.setSetPriceUnitID(value(getListValue("SetPriceUnitID", "Name", jsonObject)));
        //库存单位
        receiveEntry.setStockUnitIDName(value(getListValue("StockUnitID", "Name", jsonObject)));
        //合格入库库存关联数量(基本单位)
        receiveEntry.setStockJoinBaseQTY(value(jsonObject.getDouble("STOCKJOINBASEQTY"), 0.0));
        //退料库存关联数量(基本单位)
        receiveEntry.setReturnStkJNBaseQty(value(jsonObject.getDouble("RETURNSTKJNBASTQTY"), 0.0));
        //关联应付数量(库存基本)
        receiveEntry.setStockBaseAPJoinQty(value(jsonObject.getDouble("StockBaseAPJoinQty"), 0.0));
        //供应商交货数量
        receiveEntry.setSupDelQty(value(jsonObject.getDouble("SupDelQty"), 0.0));
        //执行人
        receiveEntry.setExecuteId(value(getJsonValue("EXECUTEID", "Name", jsonObject)));
        //产品类型
        receiveEntry.setRowType(value(jsonObject.getString("RowType")));
        //研发项目
        receiveEntry.setFResearchProject(value(getListValue("FResearchProject", "FDataValue", jsonObject)));
        //验收比例(%)
        receiveEntry.setAccRate(value(jsonObject.getDouble("ACCRATE"), 0.0));
        //订单数量
        receiveEntry.setFPoQty(value(jsonObject.getDouble("FPOQTY"), 0.0));
        //订单规格型号
        //修复字段中value含有"导致Json解析失败，替换为?
        receiveEntry.setFoldModel(value(jsonObject.getString("FoldModel")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
    }

    @Override
    public PURReceiveBill from(Map<String, Object> data) {
        return null;
    }
}
