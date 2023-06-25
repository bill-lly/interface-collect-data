package com.robot.gs.ticket.http.kingdee.result.STKInstockFiles;

import lombok.Data;

@Data
public class InStockEntry {
    //EntryId(InStockEntry)
    private Long EntryId;
    //物料编码
    private String MaterialIdNumber;
    //物料名称
    private String MaterialIdName;
    //规格型号
    private String MaterialIdSpec;
    //库存单位
    private String UnitIDName;
    //应收数量
    private Double MustQty;
    //实收数量
    private Double RealQty;
    //成本价
    private Double CostPrice;
    //仓库
    private String StockIdName;
    //含税单价
    private Double TaxPrice;
    //仓位
    private String StockLocIdSp;
    //库存状态
    private String StockStatusIdName;
    //源单类型
    private String SrcBillTypeId;
    //源单编号
    private String SRCBillNo;
    //关联应付数量（计价基本）
    private Double BaseAPJoinQty;
    //已开票数量
    private Double InvoicedQty;
    //货主类型.1
    private String OwnerTypeId;
    //货主.1
    private String OwnerIdName;
    //税率(%)
    private Double TaxRate;
    //基本单位
    private String BaseUnitIDName;
    //关联数量(基本单位)
    private Double BaseJoinQty;
    //订单单号
    private String POOrderNo;
    //税额
    private Double TaxAmount;
    //库存基本数量
    private Double BaseUnitQty;
    //材料成本
    private Double MaterialCosts;
    //材料成本(本位币)
    private Double MaterialCostsLC;
    //单价
    private Double Price;
    //系统定价
    private Double SysPrice;
    //税额(本位币)
    private Double TaxAmountLC;
    //金额（本位币）
    private Double AmountLC;
    //价格系数
    private Double PriceCoefficient;
    //折扣额
    private Double Discount;
    //退料关联数量
    private Double ReturnJoinQty;
    //计划跟踪号
    private String MtoNo;
    //供应商批号
    private String SupplierLot;
    //计价数量
    private Double PriceUnitQty;
    //数量（库存辅单位）
    private Double AuxUnitQty;
    //产品类型
    private String RowType;
    //净价
    private Double FTaxNetPrice;
    //父行标识
    private String ParentRowId;
    //总成本
    private Double BillCostAmount;
    //总成本(本位币)
    private Double BillCostAmountLC;
    //价税合计
    private Double AllAmount;
    //价税合计(本位币)
    private Double AllAmountLC;
    //已开票关联数量
    private Double InvoicedJoinQty;
    //入库类型
    private String WWInType;
    //计价基本数量
    private Double PriceBaseQty;
    //定价单位
    private String SetPriceUnitID;
    //采购单位
    private String RemainInStockUnitIdName;
    //采购数量
    private Double RemainInStockQty;
    //退料关联数量(采购基本)
    private Double RETURNSTOCKJNBASEQTY;
    //关联应付数量（库存基本)
    private Double StockBaseAPJoinQty;
    //成本价(本位币)
    private Double COSTPRICE_LC;
    //未关联应付数量（计价单位）
    private Double APNotJoinQty;
    //关联应付金额
    private Double APJoinAmount;
    //应付关闭状态
    private String PayableCloseStatus;
    //应付关闭日期
    private String PayableCloseDate;
    //研发项目
    private String FResearchProject;
    //物料说明
    private String MaterialDesc;
    //对账中
    private String IsReconciliationing;
}
