package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.SALOutstock;
import com.robot.gs.ticket.http.kingdee.result.SALOutstockFiles.SALOutstockEntry;
import com.robot.gs.udesk.integration.DataConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class SALOutstockConverter extends KingdeeDataConverter<SALOutstock> {


    public SALOutstock from(String data) {
        SALOutstock salOutstock = new SALOutstock();
        try {
            map(salOutstock, data);
        } catch (RuntimeException e) {
            log.error("获取" + SALOutstock.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return salOutstock;
    }

    private void map(SALOutstock salOutstock, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        salOutstock.setId(jsonObject.getLong("Id"));
        //日期
        salOutstock.setFDate(value(jsonObject.getString("Date")));
        //单据编号
        salOutstock.setBillNo(value(jsonObject.getString("BillNo")));
        //客户
        salOutstock.setCustomerIDName(value(getListValue("CustomerID", "Name", jsonObject)));
        //发货组织
        salOutstock.setStockOrgIdName(value(getListValue("StockOrgId", "Name", jsonObject)));
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
        salOutstock.setDocumentStatus(value(documentStatus));
        //单据类型
        salOutstock.setBillTypeIDName(value(getListValue("BillTypeID", "Name", jsonObject)));
        //销售组织
        salOutstock.setSaleOrgIdName(value(getListValue("SaleOrgId", "Name", jsonObject)));
        //销售部门
        salOutstock.setSaleDeptIDName(value(getListValue("SaleDeptID", "Name", jsonObject)));
        //销售员
        salOutstock.setSalesManIDName(value(getListValue("SalesManID", "Name", jsonObject)));
        //业务类型
        salOutstock.setBussinessType(value(jsonObject.getString("BussinessType")));
        //收货方
        salOutstock.setReceiverIDName(value(getListValue("ReceiverID", "Name", jsonObject)));
        //结算方
        salOutstock.setSettleIDName(value(getListValue("SettleID", "Name", jsonObject)));
        //付款方
        salOutstock.setPayerIDName(value(getListValue("PayerID", "Name", jsonObject)));
        //跨组织业务类型
        salOutstock.setTransferBizType(value(getListValue("TransferBizType", "Name", jsonObject)));
        //创建人
        salOutstock.setFCreatorIdName(value(getJsonValue("FCreatorId", "Name", jsonObject)));
        //创建日期
        salOutstock.setFCreateDate(value(jsonObject.getString("FCreateDate")));
        //最后修改日期
        salOutstock.setFModifyDate(value(jsonObject.getString("FModifyDate")));
        //审核人
        salOutstock.setApproverIDName(value(getJsonValue("ApproverID", "Name", jsonObject)));
        //最后修改人
        salOutstock.setFModifierIdName(value(getJsonValue("FModifierId", "Name", jsonObject)));
        //审核日期
        salOutstock.setApproveDate(value(jsonObject.getString("ApproveDate")));
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
        salOutstock.setCancelStatus(value(cancelStatus));
        //作废日期
        salOutstock.setCancelDate(value(jsonObject.getString("CancelDate")));
        //货主
        salOutstock.setOwnerIdHeadName(value(getListValue("OwnerIdHead", "Name", jsonObject)));
        //销售合同编号
        salOutstock.setFSaleContractNo(value(jsonObject.getString("F_SaleContractNo")));
        //销售收入类别
        salOutstock.setFIncomeType(value(jsonObject.getString("FIncomeType")));
        //送货地址
        salOutstock.setFReceiveAddress(value(jsonObject.getString("F_ReceiveAddress")));
        //销售区域
        salOutstock.setFGSSalesReigon(value(jsonObject.getString("F_GS_sales_reigon")));
        //销售订单
        salOutstock.setFGsSo(value(jsonObject.getString("F_gs_so")));
        //跨组织结算生成
        List<JSONObject> salOutStockFINs = jsonObject.getList("SAL_OUTSTOCKFIN", JSONObject.class) != null ? jsonObject.getList("SAL_OUTSTOCKFIN", JSONObject.class) : new ArrayList<>();
        salOutstock.setFISGENFORIOS(value((salOutStockFINs.size() > 0 ? salOutStockFINs.get(0).getString("ISGENFORIOS") : null)));
        //Entries
        List<JSONObject> salOutstockJsonEntries = jsonObject.getList("SAL_OUTSTOCKENTRY", JSONObject.class) != null ? jsonObject.getList("SAL_OUTSTOCKENTRY", JSONObject.class) : new ArrayList<>();
        SALOutstockEntry salOutstockEntry = null;
        List<SALOutstockEntry> salOutstockEntryList = new ArrayList<>();
        for (JSONObject salOutstockJsonEntry : salOutstockJsonEntries) {
            salOutstockEntry = new SALOutstockEntry();
            getSALOutstockEntry(salOutstockEntry, salOutstockJsonEntry);
            salOutstockEntryList.add(salOutstockEntry);
        }
        String gsonStrSEL = gson.toJson(salOutstockEntryList);
        salOutstock.setSALOutstockEntries(value(gsonStrSEL));
        //是否正常获取数据备注
        salOutstock.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");
    }

    private void getSALOutstockEntry(SALOutstockEntry salOutstockEntry, JSONObject salOutstockJsonEntry) {
        if (salOutstockJsonEntry == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //物料实体主键
        salOutstockEntry.setEntryId(salOutstockJsonEntry.getLong("Id"));
        //MaterialID
        JSONObject materialID = salOutstockJsonEntry.getJSONObject("MaterialID");
        //物料编码
        salOutstockEntry.setMaterialIDNumber(value(getJsonValue("MaterialID", "Number", salOutstockJsonEntry)));
        //物料名称
        salOutstockEntry.setMaterialIDName(value(getListValue("MaterialID", "Name", salOutstockJsonEntry)).replaceAll("\"", "\\?").replaceAll("\\\\", "/"));
        //规格型号
        salOutstockEntry.setMaterialIDSpecification(value(getListValue("MaterialID", "Specification", salOutstockJsonEntry)).replaceAll("\"", "\\?").replaceAll("\\\\", "/"));
        //物料类别
        salOutstockEntry.setMaterialIDMaterialGroupName(value(getListValue("MaterialGroup", "Name", materialID)));
        //应发数量
        salOutstockEntry.setMustQty(value(salOutstockJsonEntry.getDouble("MustQty"), 0.0));
        //实发数量
        salOutstockEntry.setRealQty(value(salOutstockJsonEntry.getDouble("RealQty"), 0.0));
        //仓库
        salOutstockEntry.setStockIDName(value(getListValue("StockID", "Name", salOutstockJsonEntry)));
        //仓位
        JSONObject stockLocID = salOutstockJsonEntry.getJSONObject("StockLocID");
        salOutstockEntry.setStockLocIDF100003Name(value(getListValue("F100003", "Name", stockLocID)));
        //库存状态
        salOutstockEntry.setStockStatusIDName(value(getListValue("StockStatusID", "Name", salOutstockJsonEntry)));
        //单价
        salOutstockEntry.setPrice(value(salOutstockJsonEntry.getDouble("Price"), 0.0));
        //含税单价
        salOutstockEntry.setTaxPrice(value(salOutstockJsonEntry.getDouble("TaxPrice"), 0.0));
        //是否赠品
        salOutstockEntry.setIsFree(value(salOutstockJsonEntry.getString("IsFree")));
        //金额
        salOutstockEntry.setAmount(value(salOutstockJsonEntry.getDouble("Amount"), 0.0));
        //价税合计
        salOutstockEntry.setAllAmount(value(salOutstockJsonEntry.getDouble("AllAmount"), 0.0));
        //计价单位
        salOutstockEntry.setPriceUnitIdName(value(getListValue("PriceUnitId", "Name", salOutstockJsonEntry)));
        //计价数量
        salOutstockEntry.setPriceUnitQty(value(salOutstockJsonEntry.getDouble("PriceUnitQty"), 0.0));
        //最低限价
        salOutstockEntry.setLimitDownPrice(value(salOutstockJsonEntry.getDouble("LimitDownPrice"), 0.0));
        //关联退货数量
        salOutstockEntry.setReturnQty(value(salOutstockJsonEntry.getDouble("ReturnQty"), 0.0));
        //累计退货通知数量
        salOutstockEntry.setSumRetNoticeQty(value(salOutstockJsonEntry.getDouble("SumRetNoticeQty"), 0.0));
        //累计退货数量
        salOutstockEntry.setSumRetStockQty(value(salOutstockJsonEntry.getDouble("SumRetStockQty"), 0.0));
        //累计应收数量（销售）
        salOutstockEntry.setSumInvoicedQty(value(salOutstockJsonEntry.getDouble("SumInvoicedQty"), 0.0));
        //源单编号
        salOutstockEntry.setSrcBillNo(value(salOutstockJsonEntry.getString("SrcBillNo")));
        //金额（本位币）
        salOutstockEntry.setAmountLC(value(salOutstockJsonEntry.getDouble("Amount_LC"), 0.0));
        //税额（本位币）
        salOutstockEntry.setTaxAmountLC(value(salOutstockJsonEntry.getDouble("TaxAmount_LC"), 0.0));
        //价税合计（本位币）
        salOutstockEntry.setAllAmountLC(value(salOutstockJsonEntry.getDouble("AllAmount_LC"), 0.0));
        //货主类型
        salOutstockEntry.setOwnerTypeID(value(salOutstockJsonEntry.getString("OwnerTypeID")));
        //基本单位
        salOutstockEntry.setBaseUnitIDName(value(getListValue("BaseUnitID", "Name", salOutstockJsonEntry)));
        //货主
        salOutstockEntry.setOwnerIDName(value(getListValue("OwnerID", "Name", salOutstockJsonEntry)));
        //保管者类型
        salOutstockEntry.setKeeperTypeID(value(salOutstockJsonEntry.getString("KeeperTypeID")));
        //订单单号
        salOutstockEntry.setSoorDerno(value(salOutstockJsonEntry.getString("SoorDerno")));
        //保管者
        salOutstockEntry.setKeeperIDName(value(getListValue("KeeperID", "Name", salOutstockJsonEntry)));
        //库存基本数量
        salOutstockEntry.setBaseUnitQty(value(salOutstockJsonEntry.getDouble("BaseUnitQty"), 0.0));
        //实收数量
        salOutstockEntry.setActQty(value(salOutstockJsonEntry.getDouble("ActQty"), 0.0));
        //累计退货数量(基本单位)
        salOutstockEntry.setBaseSumRetstockQty(value(salOutstockJsonEntry.getDouble("BaseSumRetstockQty"), 0.0));
        //累计退货通知数量(销售基本)
        salOutstockEntry.setBaseSumRetNoticeQty(value(salOutstockJsonEntry.getDouble("BaseSumRetNoticeQty"), 0.0));
        //消耗汇总
        salOutstockEntry.setIsConsumeSum(value(salOutstockJsonEntry.getString("IsConsumeSum")));
        //关联应收数量
        salOutstockEntry.setARJoinQty(value(salOutstockJsonEntry.getDouble("ARJoinQty"), 0.0));
        //控制出库数量
        salOutstockEntry.setOutControl(value(salOutstockJsonEntry.getString("OUTCONTROL")));
        //销售单位
        salOutstockEntry.setSalUnitIdName(value(getListValue("SalUnitId", "Name", salOutstockJsonEntry)));
        //销售数量
        salOutstockEntry.setSalUnitQty(value(salOutstockJsonEntry.getDouble("SALUNITQTY"), 0.0));
        //销售基本数量
        salOutstockEntry.setSalBaseQty(value(salOutstockJsonEntry.getDouble("SALBASEQTY"), 0.0));
        //计价基本数量
        salOutstockEntry.setPriceBaseQty(value(salOutstockJsonEntry.getDouble("PRICEBASEQTY"), 0.0));
        //关联应收数量（库存基本）
        salOutstockEntry.setStockBaseARJoinQty(value(salOutstockJsonEntry.getDouble("StockBaseARJoinQty"), 0.0));
        //关联退货数量（库存基本）
        salOutstockEntry.setStockBaseReturnQty(value(salOutstockJsonEntry.getDouble("StockBaseReturnQty"), 0.0));
        //累计退货数量（库存基本）
        salOutstockEntry.setStockBaseSumRetStockQty(value(salOutstockJsonEntry.getDouble("StockBaseSumRetStockQty"), 0.0));
        //明细货主供应商
        salOutstockEntry.setEOwnerSupplierIdName(value(getListValue("EOwnerSupplierId", "Name", salOutstockJsonEntry)));
        //关联应收数量（销售基本）
        salOutstockEntry.setSalBaseARJoinQty(value(salOutstockJsonEntry.getDouble("SalBaseARJoinQty"), 0.0));
        //产品类型
        salOutstockEntry.setRowType(value(salOutstockJsonEntry.getString("RowType")));
        //管易是否到账
        salOutstockEntry.setFGYFinStatus(value(salOutstockJsonEntry.getString("FGYFINSTATUS")));
        //尾差处理标识
        salOutstockEntry.setTailDiffFlag(value(salOutstockJsonEntry.getString("TailDiffFlag")));
        //VMI业务
        salOutstockEntry.setVmiBusinessStatus(value(salOutstockJsonEntry.getString("VmiBusinessStatus")));
    }

    @Override
    public SALOutstock from(Map<String, Object> data) {
        return null;
    }
}
