package com.robot.gs.ticket.http.kingdee.result.SALReturnstockFiles;

import lombok.Data;

@Data
public class SALReturnstockEntry {
    //实体主键
    private Long EntryId;
    //物料编码
    private String MaterialIdNumber;
    //物料名称
    private String MaterialIdName;
    //规格型号
    private String MaterialIdSpecification;
    //物料类别
    private String MaterialIdMaterialGroupName;
    //库存单位
    private String UnitIdName;
    //应退数量
    private Double Mustqty;
    //实退数量
    private Double RealQty;
    //实退数量（辅单位）
    private Double ExtAuxUnitQty;
    //退货日期
    private String DeliveryDate;
    //退货类型
    private String ReturnTypeFDataValue;
    //仓库
    private String StockIdName;
    //仓位
    private String StocklocIdF100003Name;
    //仓位_生产物料仓仓位_编码
    private String StocklocIdF100003Number;
    //库存状态
    private String StockstatusIdName;
    //备注
    private String Note;
    //单价
    private Double Price;
    //含税单价
    private Double TaxPrice;
    //税组合
    private Double TaxCombination;
    //税率(%)
    private Double TaxRate;
    //金额
    private Double Amount;
    //税额
    private Double TaxAmount;
    //价税合计
    private Double AllAmount;
    //计价单位
    private String PriceUnitIdName;
    //计价数量
    private Double PriceUnitQty;
    //折扣率(%)
    private Double DiscountRate;
    //折扣额
    private Double Discount;
    //净价
    private Double TaxNetPrice;
    //金额（本位币）
    private Double AmountLC;
    //税额（本位币）
    private Double TaxAmountLC;
    //价税合计（本位币）
    private Double AllAmountLC;
    //是否赠品
    private String IsFree;
    //基本单位
    private String BaseunitIdName;
    //货主类型
    private String OwnerTypeID;
    //货主
    private String OwnerIdName;
    //保管者类型
    private String KeeperTypeId;
    //保管者
    private String KeeperIdName;
    //库存更新标示
    private String StockFlag;
    //业务流程
    private String FBFLowId;
    //源单类型
    private String SrcBillTypeID;
    //源单编号
    private String SrcBillNo;
    //订单类型
    private String SoBillTypeID;
    //订单单号
    private String OrderNo;
    //计价基本数量
    private Double PriceBaseQty;
    //销售单位
    private String SalUnitIDName;
    //销售数量
    private Double SalUnitQty;
    //销售基本数量
    private Double SalBaseQty;
    //质量类型
    private String QualifyType;
    //销售基本分子
    private Double SalBaseNum;
    //库存基本分母
    private Double StockBaseDen;
    //关联应收数量（库存基本）
    private Double FStockBaseARJoinQty;
    //明细货主供应商
    private String EOwnerSupplierIdName;
    //组织间结算跨法人标识
    private String IsOverLegalOrg;
    //明细结算组织客户
    private String ESettleCustomerIdName;
    //关联应收数量（销售基本）
    private Double SalBaseARJoinQty;
    //产品类型
    private String RowType;
    //VMI业务
    private String VmiBusinessStatus;
}
