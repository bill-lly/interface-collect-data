package com.robot.gs.ticket.http.kingdee.result.SALOutstockFiles;

import lombok.Data;

@Data
public class SALOutstockEntry {
    //实体主键
    private Long EntryId;
    //物料编码
    private String MaterialIDNumber;
    //物料名称
    private String MaterialIDName;
    //规格型号
    private String MaterialIDSpecification;
    //物料类别
    private String MaterialIDMaterialGroupName;
    //应发数量
    private Double MustQty;
    //实发数量
    private Double RealQty;
    //仓库
    private String StockIDName;
    //仓位
    private String StockLocIDF100003Name;
    //库存状态
    private String StockStatusIDName;
    //单价
    private Double Price;
    //含税单价
    private Double TaxPrice;
    //是否赠品
    private String IsFree;
    //金额
    private Double Amount;
    //价税合计
    private Double AllAmount;
    //计价单位
    private String PriceUnitIdName;
    //计价数量
    private Double PriceUnitQty;
    //最低限价
    private Double LimitDownPrice;
    //关联退货数量
    private Double ReturnQty;
    //累计退货通知数量
    private Double SumRetNoticeQty;
    //累计退货数量
    private Double SumRetStockQty;
    //累计应收数量（销售）
    private Double SumInvoicedQty;
    //源单编号
    private String SrcBillNo;
    //金额（本位币）
    private Double AmountLC;
    //税额（本位币）
    private Double TaxAmountLC;
    //价税合计（本位币）
    private Double AllAmountLC;
    //货主类型
    private String OwnerTypeID;
    //基本单位
    private String BaseUnitIDName;
    //货主
    private String OwnerIDName;
    //保管者类型
    private String KeeperTypeID;
    //订单单号
    private String SoorDerno;
    //保管者
    private String KeeperIDName;
    //库存基本数量
    private Double BaseUnitQty;
    //实收数量
    private Double ActQty;
    //累计退货数量(基本单位)
    private Double BaseSumRetstockQty;
    //累计退货通知数量(销售基本)
    private Double BaseSumRetNoticeQty;
    //消耗汇总
    private String IsConsumeSum;
    //关联应收数量
    private Double ARJoinQty;
    //控制出库数量
    private String OutControl;
    //销售单位
    private String SalUnitIdName;
    //销售数量
    private Double SalUnitQty;
    //销售基本数量
    private Double SalBaseQty;
    //计价基本数量
    private Double PriceBaseQty;
    //关联应收数量（库存基本）
    private Double StockBaseARJoinQty;
    //关联退货数量（库存基本）
    private Double StockBaseReturnQty;
    //累计退货数量（库存基本）
    private Double StockBaseSumRetStockQty;
    //明细货主供应商
    private String EOwnerSupplierIdName;
    //关联应收数量（销售基本）
    private Double SalBaseARJoinQty;
    //产品类型
    private String RowType;
    //管易是否到账
    private String FGYFinStatus;
    //尾差处理标识
    private String TailDiffFlag;
    //VMI业务
    private String VmiBusinessStatus;
}
