package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.SALReturnstock;
import com.robot.gs.ticket.http.kingdee.result.SALOutstockFiles.SALOutstockEntry;
import com.robot.gs.ticket.http.kingdee.result.SALReturnstockFiles.SALReturnstockEntry;
import com.robot.gs.udesk.integration.DataConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SALReturnstockConverter extends KingdeeDataConverter<SALReturnstock> {
    public SALReturnstock from(String data) {
        SALReturnstock salReturnstock = new SALReturnstock();
        try {
            map(salReturnstock, data);
        } catch (RuntimeException e) {
            log.error("获取" + SALReturnstock.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return salReturnstock;
    }

    private void map(SALReturnstock salReturnstock, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        salReturnstock.setId(jsonObject.getLong("Id"));
        //单据类型
        salReturnstock.setBillTypeIDName(value(getListValue("BillTypeID", "Name", jsonObject)));
        //日期
        salReturnstock.setFDate(value(jsonObject.getString("Date")));
        //单据编号
        salReturnstock.setBillNo(value(jsonObject.getString("BillNo")));
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
        salReturnstock.setDocumentStatus(value(documentStatus));
        //销售组织
        salReturnstock.setSaleOrgIdName(value(getListValue("SaleOrgId", "Name", jsonObject)));
        //销售部门
        salReturnstock.setSledeptidName(value(getListValue("Sledeptid", "Name", jsonObject)));
        //退货客户
        salReturnstock.setRetcustIdName(value(getListValue("RetcustId", "Name", jsonObject)));
        //退货原因
        salReturnstock.setReturnReason(value(getListValue("ReturnReason", "FDataValue", jsonObject)));
        //交货地点
        salReturnstock.setHeadLocId(value(jsonObject.getString("HeadLocId")));
        //销售组
        salReturnstock.setSaleGroupIdName(value(getListValue("SaleGroupId", "Name", jsonObject)));
        //销售员
        salReturnstock.setSalesManIdName(value(getListValue("SalesManId", "Name", jsonObject)));
        //库存组织
        salReturnstock.setStockOrgIdName(value(getListValue("StockOrgId", "Name", jsonObject)));
        //库存部门
        salReturnstock.setStockDeptIdName(value(getListValue("StockDeptId", "Name", jsonObject)));
        //库存组
        salReturnstock.setStockerGroupIdName(value(getListValue("StockerGroupId", "Name", jsonObject)));
        //仓管员
        salReturnstock.setStockerIdName(value(getListValue("StockerId", "Name", jsonObject)));
        //业务类型
        salReturnstock.setBussinessType(value(jsonObject.getString("BussinessType")));
        //收货方
        salReturnstock.setReceiveCustIdName(value((getListValue("ReceiveCustId", "Name", jsonObject))));
        //收货人姓名
        salReturnstock.setFLinkMan(value(jsonObject.getString("FLinkMan")));
        //收货方地址
        salReturnstock.setReceiveAddress(value(jsonObject.getString("ReceiveAddress")));
        //收货方联系人
        salReturnstock.setReceiveCusContact(value(jsonObject.getString("ReceiveCusContact")));
        //结算方
        salReturnstock.setSettleCustIdName(value(getListValue("SettleCustId", "Name", jsonObject)));
        //联系电话
        salReturnstock.setFLinkPhone(value(jsonObject.getString("FLinkPhone")));
        //付款方
        salReturnstock.setPayCustIdName(value(getListValue("PayCustId", "Name", jsonObject)));
        //对应组织
        salReturnstock.setCorrespondOrgId(value((getListValue("CorrespondOrgId", "Name", jsonObject))));
        //货主类型
        salReturnstock.setOwnerTypeIdHead(value(jsonObject.getString("OwnerTypeIdHead")));
        //货主
        salReturnstock.setOwnerIdHeadName(value(getListValue("OwnerIdHead", "Name", jsonObject)));
        //创建人
        JSONObject creatorId = jsonObject.getJSONObject("CreatorId");
        salReturnstock.setCreatorIdName(value(creatorId != null ? creatorId.getString("Name") : null));
        //创建日期
        salReturnstock.setCreateDate(value(jsonObject.getString("CreateDate")));
        //最后修改人
        JSONObject modifierId = jsonObject.getJSONObject("ModifierId");
        salReturnstock.setModifierIdName(value(modifierId != null ? modifierId.getString("Name") : null));
        //最后修改日期
        salReturnstock.setModifyDate(value(jsonObject.getString("ModifyDate")));
        //审核人
        salReturnstock.setApproverIdName(value(getJsonValue("ApproverId", "Name", jsonObject)));
        //审核日期
        salReturnstock.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //作废状态
        salReturnstock.setCancelStatus(value(jsonObject.getString("CancelStatus")));
        //作废人
        salReturnstock.setCancellerIdName(value(getJsonValue("CancellerId", "Name", jsonObject)));
        //作废日期
        salReturnstock.setCancelDate(value(jsonObject.getString("CancelDate")));
        //整单服务标识
        salReturnstock.setIsTotalServiceOrCost(value(jsonObject.getString("IsTotalServiceOrCost")));
        //跨组织结算生成
        List<JSONObject> salReturnStockFINs = jsonObject.getList("SAL_RETURNSTOCKFIN", JSONObject.class) != null ? jsonObject.getList("SAL_RETURNSTOCKFIN", JSONObject.class) : new ArrayList<>();
        salReturnstock.setFISGENFORIOS(value((salReturnStockFINs.size() > 0 ? salReturnStockFINs.get(0).getString("ISGENFORIOS") : null)));
        //Entries
        List<JSONObject> salReturnstockJsonEntries = jsonObject.getList("SAL_RETURNSTOCKENTRY", JSONObject.class) != null ? jsonObject.getList("SAL_RETURNSTOCKENTRY", JSONObject.class) : new ArrayList<>();
        SALReturnstockEntry salReturnstockEntry = null;
        List<SALReturnstockEntry> salReturnstockEntryList = new ArrayList<>();
        for (JSONObject salReturnstockJsonEntry : salReturnstockJsonEntries) {
            salReturnstockEntry = new SALReturnstockEntry();
            getSALReturnstockEntry(salReturnstockEntry, salReturnstockJsonEntry);
            salReturnstockEntryList.add(salReturnstockEntry);
        }
        String gsonStrSREL = gson.toJson(salReturnstockEntryList);
        salReturnstock.setSALReturnstockEntries(value(gsonStrSREL));
        //是否正常获取数据备注
        salReturnstock.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void getSALReturnstockEntry(SALReturnstockEntry salReturnstockEntry, JSONObject salReturnstockJsonEntry) {
        if (salReturnstockJsonEntry == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //物料实体主键
        salReturnstockEntry.setEntryId(salReturnstockJsonEntry.getLong("Id"));
        //实退数量（辅单位）
        salReturnstockEntry.setExtAuxUnitQty(value(salReturnstockJsonEntry.getDouble("ExtAuxUnitQty"), 0.0));
        //MaterialID
        JSONObject materialId = salReturnstockJsonEntry.getJSONObject("MaterialId");
        //物料编码
        salReturnstockEntry.setMaterialIdNumber(value(getJsonValue("MaterialId", "Number", salReturnstockJsonEntry)));
        //物料名称
        salReturnstockEntry.setMaterialIdName(value(getListValue("MaterialId", "Name", salReturnstockJsonEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //规格型号
        salReturnstockEntry.setMaterialIdSpecification(value(getListValue("MaterialId", "Specification", salReturnstockJsonEntry)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //物料类别
        salReturnstockEntry.setMaterialIdMaterialGroupName(value(getListValue("MaterialGroup", "Name", materialId)));
        //库存单位
        salReturnstockEntry.setUnitIdName(value(getListValue("UnitId", "Name", salReturnstockJsonEntry)));
        //应退数量
        salReturnstockEntry.setMustqty(value(salReturnstockJsonEntry.getDouble("Mustqty"), 0.0));
        //实退数量
        salReturnstockEntry.setRealQty(value(salReturnstockJsonEntry.getDouble("RealQty"), 0.0));
        //退货日期
        salReturnstockEntry.setDeliveryDate(value(salReturnstockJsonEntry.getString("DeliveryDate")));
        //退货类型
        salReturnstockEntry.setReturnTypeFDataValue(value(getListValue("ReturnType", "FDataValue", salReturnstockJsonEntry)));
        //仓库
        salReturnstockEntry.setStockIdName(value(getListValue("StockId", "Name", salReturnstockJsonEntry)));
        //stockLocId
        JSONObject stockLocId = salReturnstockJsonEntry.getJSONObject("StocklocId");
        //仓位
        salReturnstockEntry.setStocklocIdF100003Name(value(getListValue("F100003", "Name", stockLocId)));
        //仓位_生产物料仓仓位_编码
        //salReturnstockEntry.setStocklocIdF100003Number(value(stockLocId != null ? getListValue("F100003", "Number", salReturnstockJsonEntry): null));
        salReturnstockEntry.setStocklocIdF100003Number(value(getJsonValue("F100003", "Number", stockLocId)));
        //库存状态
        salReturnstockEntry.setStockstatusIdName(value(getListValue("StockstatusId", "Name", salReturnstockJsonEntry)));
        //备注
        salReturnstockEntry.setNote(value(salReturnstockJsonEntry.getString("Note")).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //单价
        salReturnstockEntry.setPrice(value(salReturnstockJsonEntry.getDouble("Price"), 0.0));
        //含税单价
        salReturnstockEntry.setTaxPrice(value(salReturnstockJsonEntry.getDouble("TaxPrice"), 0.0));
        //税组合
        salReturnstockEntry.setTaxCombination(value(salReturnstockJsonEntry.getDouble("TaxCombination"), 0.0));
        //税率(%)
        salReturnstockEntry.setTaxRate(value(salReturnstockJsonEntry.getDouble("TaxRate"), 0.0));
        //金额
        salReturnstockEntry.setAmount(value(salReturnstockJsonEntry.getDouble("Amount"), 0.0));
        //税额
        salReturnstockEntry.setTaxAmount(value(salReturnstockJsonEntry.getDouble("TaxAmount"), 0.0));
        //价税合计
        salReturnstockEntry.setAllAmount(value(salReturnstockJsonEntry.getDouble("AllAmount"), 0.0));
        //计价单位
        salReturnstockEntry.setPriceUnitIdName(value(getListValue("PriceUnitId", "Name", salReturnstockJsonEntry)));
        //计价数量
        salReturnstockEntry.setPriceUnitQty(value(salReturnstockJsonEntry.getDouble("PriceUnitQty"), 0.0));
        //折扣率(%)
        salReturnstockEntry.setDiscountRate(value(salReturnstockJsonEntry.getDouble("DiscountRate"), 0.0));
        //折扣额
        salReturnstockEntry.setDiscount(value(salReturnstockJsonEntry.getDouble("Discount"), 0.0));
        //净价
        salReturnstockEntry.setTaxNetPrice(value(salReturnstockJsonEntry.getDouble("TaxNetPrice"), 0.0));
        //金额（本位币）
        salReturnstockEntry.setAmountLC(value(salReturnstockJsonEntry.getDouble("Amount_LC"), 0.0));
        //税额（本位币）
        salReturnstockEntry.setTaxAmountLC(value(salReturnstockJsonEntry.getDouble("TaxAmount_LC"), 0.0));
        //价税合计（本位币）
        salReturnstockEntry.setAllAmountLC(value(salReturnstockJsonEntry.getDouble("AllAmount_LC"), 0.0));
        //是否赠品
        salReturnstockEntry.setIsFree(value(salReturnstockJsonEntry.getString("IsFree")));
        //基本单位
        salReturnstockEntry.setBaseunitIdName(value(getListValue("BaseunitId", "Name", salReturnstockJsonEntry)));
        //货主类型
        salReturnstockEntry.setOwnerTypeID(value(salReturnstockJsonEntry.getString("OwnerTypeId")));
        //货主
        salReturnstockEntry.setOwnerIdName(value(getListValue("OwnerId", "Name", salReturnstockJsonEntry)));
        //保管者类型
        salReturnstockEntry.setKeeperTypeId(value(salReturnstockJsonEntry.getString("KeeperTypeId")));
        //保管者
        salReturnstockEntry.setKeeperIdName(value(getListValue("KeeperId", "Name", salReturnstockJsonEntry)));
        //库存更新标示
        salReturnstockEntry.setStockFlag(value(salReturnstockJsonEntry.getString("STOCKFLAG")));
        //业务流程
        salReturnstockEntry.setFBFLowId(value(salReturnstockJsonEntry.getString("FBFLowId")));
        //源单类型
        salReturnstockEntry.setSrcBillTypeID(value(salReturnstockJsonEntry.getString("SrcBillTypeID")));
        //源单编号
        salReturnstockEntry.setSrcBillNo(value(salReturnstockJsonEntry.getString("SrcBillNo")));
        //订单类型
        salReturnstockEntry.setSoBillTypeID(value(getListValue("SOBILLTYPEID", "Name", salReturnstockJsonEntry)));
        //订单单号
        salReturnstockEntry.setOrderNo(value(salReturnstockJsonEntry.getString("OrderNo")));
        //计价基本数量
        salReturnstockEntry.setPriceBaseQty(value(salReturnstockJsonEntry.getDouble("PriceBaseQty"), 0.0));
        //销售单位
        salReturnstockEntry.setSalUnitIDName(value(getListValue("SalUnitID", "Name", salReturnstockJsonEntry)));
        //销售数量
        salReturnstockEntry.setSalUnitQty(value(salReturnstockJsonEntry.getDouble("SalUnitQty"), 0.0));
        //销售基本数量
        salReturnstockEntry.setSalBaseQty(value(salReturnstockJsonEntry.getDouble("SalBaseQty"), 0.0));
        //质量类型
        salReturnstockEntry.setQualifyType(value(salReturnstockJsonEntry.getString("QualifyType")));
        //销售基本分子
        salReturnstockEntry.setSalBaseNum(value(salReturnstockJsonEntry.getDouble("SalBaseNum"), 0.0));
        //库存基本分母
        salReturnstockEntry.setStockBaseDen(value(salReturnstockJsonEntry.getDouble("StockBaseDen"), 0.0));
        //关联应收数量（库存基本）
        salReturnstockEntry.setFStockBaseARJoinQty(value(salReturnstockJsonEntry.getDouble("FStockBaseARJoinQty"), 0.0));
        //明细货主供应商
        salReturnstockEntry.setEOwnerSupplierIdName(value(getListValue("EOwnerSupplierId", "Name", salReturnstockJsonEntry)));
        //组织间结算跨法人标识
        salReturnstockEntry.setIsOverLegalOrg(value(salReturnstockJsonEntry.getString("IsOverLegalOrg")));
        //明细结算组织客户
        salReturnstockEntry.setESettleCustomerIdName(value(getListValue("ESettleCustomerId", "Name", salReturnstockJsonEntry)));
        //关联应收数量（销售基本）
        salReturnstockEntry.setSalBaseARJoinQty(value(salReturnstockJsonEntry.getDouble("SalBaseARJoinQty"), 0.0));
        //产品类型
        salReturnstockEntry.setRowType(value(salReturnstockJsonEntry.getString("RowType")));
        //VMI业务
        salReturnstockEntry.setVmiBusinessStatus(value(salReturnstockJsonEntry.getString("VmiBusinessStatus")));
    }

    @Override
    public SALReturnstock from(Map<String, Object> data) {
        return null;
    }
}
