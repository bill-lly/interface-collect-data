package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.PURRequisition;
import com.robot.gs.ticket.http.kingdee.result.PURRequisitionFiles.ReqEntry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class PURRequisitionConverter extends KingdeeDataConverter<PURRequisition> {
    public PURRequisition from(String data) {
        PURRequisition purRequisition = new PURRequisition();
        try {
            map(purRequisition, data);
        } catch (RuntimeException e) {
            log.error("获取" + PURRequisition.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return purRequisition;
    }

    private void map(PURRequisition purRequisition, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        purRequisition.setId(jsonObject.getLong("Id"));
        //单据类型
        purRequisition.setBillTypeIDIdName(value(getListValue("BillTypeID", "Name", jsonObject)));
        //创建人
        purRequisition.setCreatorIdName(value(getJsonValue("CreatorId", "Name", jsonObject)));
        //单据编号
        purRequisition.setBillNo(value(jsonObject.getString("BillNo")));
        //创建日期
        purRequisition.setCreateDate(value(jsonObject.getString("CreateDate")));
        //最后修改人
        purRequisition.setModifierIdName(value(getJsonValue("ModifierId", "Name", jsonObject)));
        //最后修改日期
        purRequisition.setModifyDate(value(jsonObject.getString("ModifyDate")));
        //申请类型
        purRequisition.setRequestType(value(jsonObject.getString("RequestType")));
        //数据状态
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
        purRequisition.setDocumentStatus(value(documentStatus));
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
        purRequisition.setCloseStatus(value(closeStatus));
        //申请组织
        purRequisition.setApplicationOrgIdName(value(getListValue("ApplicationOrgId", "Name", jsonObject)));
        //审核人
        purRequisition.setFApprovePersonName(value(getListValue("F_ApprovePerson", "Name", jsonObject)));
        //审核日期
        purRequisition.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //申请人
        purRequisition.setApplicantIdName(value(getListValue("ApplicantId", "Name", jsonObject)));
        //关闭人
        purRequisition.setCloserIdName(value(getJsonValue("CloserId", "Name", jsonObject)));
        //申请部门
        purRequisition.setApplicationDeptIdName(value(getListValue("ApplicationDeptId", "Name", jsonObject)));
        //关闭日期
        purRequisition.setCloseDate(value(jsonObject.getString("CloseDate")));
        //币别
        purRequisition.setCurrencyIdName(value(getListValue("CurrencyId", "Name", jsonObject)));
        //含税金额合计
        purRequisition.setTotalAmount(value(jsonObject.getDouble("TotalAmount"), 0.0));
        //作废人
        purRequisition.setCancellerIdName(value(getJsonValue("CancellerId", "Name", jsonObject)));
        //作废日期
        purRequisition.setCancelDate(value(jsonObject.getString("CancelDate")));
        //合并作废
        purRequisition.setIsMergeCancel(value(jsonObject.getString("IsMergeCancel")));
        //手工关闭
        purRequisition.setManualClose(value(jsonObject.getString("MANUALCLOSE")));
        //备注
        purRequisition.setNote(value(jsonObject.getString("Note")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //申请日期
        purRequisition.setApplicationDate(value(jsonObject.getString("ApplicationDate")));
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
        purRequisition.setCancelStatus(value(cancelStatus));
        //本部门终审人
        purRequisition.setFinApprovePersonName(value(getListValue("F_ApprovePerson", "Name", jsonObject)));
        //MRP运算号
        purRequisition.setFMRPNo(value(jsonObject.getString("F_MRPNo")));
        //收货地址
        String receiveAddress = "";
        if (jsonObject.getString("F_ora_Combo") != null) {
            switch (jsonObject.getString("F_ora_Combo")) {
                case "1":
                    receiveAddress = "江苏省苏州市张家港市凤凰镇友谊路211号 苏州高之仙自动化科技有限公司 吴健勇 电话：17751316587";
                    break;
                case "2":
                    receiveAddress = "江苏省苏州市工业园区双泾街59号雅景高新科技园 苏州高之澄机器人智能科技有限公司 郝耀锋  15036356929";
                    break;
                case "5":
                    receiveAddress = "上海市浦东新区秀浦路2388号13号楼807(仅创新业务BU用)，收件人 汪强 电话15776760616";
                    break;
                case "0":
                    receiveAddress = "空";
                    break;
                case "3":
                    receiveAddress = "上海市浦东新区盛夏路666号D栋2楼(仅AITO用) 收件人：AIOT-BU 13918682013";
                    break;
                case "4":
                    receiveAddress = "上海市闵行区马桥镇中辉路60号10号楼(仅环境BU用)  汪伟13966137131";
                    break;
                case "6":
                    receiveAddress = "资阳智能制造产业园（清泉片区）标准厂房一期一批次16号楼  周小平 13320888339";
                    break;
                default:
                    receiveAddress = null;
                    break;
            }
        }
        purRequisition.setFOraCombo(value(receiveAddress));
        //ReqEntry
        List<JSONObject> reqEntrys = jsonObject.getList("ReqEntry", JSONObject.class) != null ? jsonObject.getList("ReqEntry", JSONObject.class) : new ArrayList<>();
        List<ReqEntry> reqEntrylist = new ArrayList<>();
        ReqEntry entry = null;
        for (JSONObject reqEntry : reqEntrys) {
            entry = new ReqEntry();
            getReqEntry(entry, reqEntry);
            reqEntrylist.add(entry);
        }
        String gsonStrCl1 = gson.toJson(reqEntrylist);
        purRequisition.setReqEntryList(value(gsonStrCl1));
        //是否正常获取数据备注
        purRequisition.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void getReqEntry(ReqEntry entry, JSONObject reqEntry) {
        if (reqEntry == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //EntryId
        entry.setEntryId(reqEntry.getLong("Id"));
        //物料编码
        JSONObject materialId = (JSONObject) reqEntry.get("MaterialId");
        entry.setMaterialIdNumber(value(materialId != null ? materialId.getString("Number") : null));
        //物料名称
        entry.setMaterialIdName(value(getListValue("MaterialId", "Name", reqEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //物料说明
        entry.setMaterialDesc(value(getSimListValue("MaterialDesc", reqEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //规格型号
        //修复字段中value含有"导致Json解析失败，替换为?
        entry.setFoldModel(value(getListValue("MaterialId", "Specification", reqEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //物料类别
        entry.setMaterialGroupName(value(getListValue("MaterialGroup", "Name", materialId)));
        //辅助属性
        entry.setMaterialAuxPty(value(reqEntry.getString("AuxPropId")));
        //申请单位
        entry.setUnitIdName(value(getListValue("UnitID", "Name", reqEntry)));
        //申请数量
        entry.setQty(value(reqEntry.getDouble("ReqQty"), 0.0));
        //申请数量(基本单位)
        entry.setBaseReqQty(value(reqEntry.getDouble("BaseReqQty"), 0.0));
        //批准数量
        entry.setApproveQty(value(reqEntry.getDouble("ApproveQty"), 0.0));
        //到货日期
        entry.setArrivalDate(value(reqEntry.getString("ArrivalDate")));
        //提前期
        entry.setLeadTime(value(reqEntry.getString("LeadTime")));
        //采购组织
        entry.setPurchaseOrgIdName(value(getListValue("PurchaseOrgId", "Name", reqEntry)));
        //建议供应商
        entry.setSuggestSupplierIdName(value(getListValue("SuggestSupplierId", "Name", reqEntry)));
        //备注.1
        entry.setEntryNote(value(reqEntry.getString("EntryNote")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //计价单位
        entry.setPriceUnitIdName(value(getListValue("PriceUnitId", "Name", reqEntry)));
        //单价
        entry.setEvaluatePrice(value(reqEntry.getDouble("EvaluatePrice"), 0.0));
        //计价数量
        entry.setPriceUnitQty(value(reqEntry.getDouble("PriceUnitQty"), 0.0));
        //含税金额
        entry.setReqAmount(value(reqEntry.getDouble("ReqAmount"), 0.0));
        //需求组织
        entry.setRequireOrgIdName(value(getListValue("RequireOrgId", "Name", reqEntry)));
        //订单数量
        entry.setOrderQty(value(reqEntry.getDouble("OrderQty"), 0.0));
        //剩余数量
        entry.setRemainQty(value(reqEntry.getDouble("RemainQty"), 0.0));
        //入库数量
        entry.setStockQty(value(reqEntry.getDouble("StockQty"), 0.0));
        //收料数量
        entry.setReceiveQty(value(reqEntry.getDouble("ReceiveQty"), 0.0));
        //终止日期
        entry.setTerminateDate(value(reqEntry.getString("TerminateDate")));
        //批准数量(基本单位)
        entry.setFBaseUnitQty(value(reqEntry.getDouble("FBaseUnitQty"), 0.0));
        //业务关闭
        String closeStatus = "";
        if (reqEntry.getString("MRPCloseStatus") != null) {
            switch (reqEntry.getString("MRPCloseStatus")) {
                case "A":
                    closeStatus = "正常";
                    break;
                case "B":
                    closeStatus = "业务关闭";
                    break;
                default:
                    closeStatus = null;
                    break;
            }
        }
        entry.setMRPCloseStatus(value(closeStatus));
        //计划确认
        entry.setPlanConfirm(value(reqEntry.getString("PlanConfirm")));
        //需求跟踪号
        entry.setReqTraceNo(value(reqEntry.getString("REQTRACENO")));
        //含税单价
        entry.setTaxPrice(value(reqEntry.getDouble("TAXPRICE"), 0.0));
        //VMI业务
        entry.setIsVmiBusiness(value(reqEntry.getString("IsVmiBusiness")));
        //产品类型
        entry.setRowType(value(reqEntry.getString("RowType")));
        //研发项目
        entry.setFResearchProject(value(getListValue("F_ResearchProject", "FDataValue", reqEntry)));
        //所属采购员
        List<JSONObject> materialPurchases = materialId != null ? (materialId.getList("MaterialPurchase", JSONObject.class) != null ? materialId.getList("MaterialPurchase", JSONObject.class) : new ArrayList<>()) : new ArrayList<>();
        String name = materialPurchases.size() > 0 ? getListValue("PurchaserId", "Name", (JSONObject) materialPurchases.get(0)) : null;
        entry.setPurchaserIdName(value(name));
        //采购链接
        entry.setFGsPlink(value(reqEntry.getString("F_gs_plink")));
        //旧物料编码
        entry.setMaterialIdOldNumber(value(materialId != null ? materialId.getString("OldNumber") : null));
    }


    @Override
    public PURRequisition from(Map<String, Object> data) {
        return null;
    }
}
