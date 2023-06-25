package com.robot.gs.kingdee.bean;

import lombok.Data;

//采购入库单
@Data
public class STKInstock {
    //实体主键
    private Long Id;
    //单据类型
    private String FBillTypeIDName;
    //应付状态
    private String APStatus;
    //创建人
    private String CreatorIdName;
    //供货方
    private String SupplyIdName;
    //创建日期
    private String CreateDate;
    //结算方
    private String SettleIdName;
    //业务类型
    private String BusinessType;
    //单据编号
    private String BillNo;
    //最后修改人
    private String FModifierIdName;
    //收款方
    private String ChargeIdName;
    //入库日期
    private String Date;
    //最后修改日期
    private String FModifyDate;
    //供应商
    private String SupplierIdName;
    //审核人
    private String ApproverIdName;
    //收料组织
    private String StockOrgIdName;
    //货主
    private String OwnerIdHeadName;
    //审核日期
    private String ApproveDate;
    //收料部门
    private String StockDeptIdName;
    //作废人
    private String CancellerIdName;
    //货主类型
    private String OwnerTypeIdHead;
    //库存组
    private String StockerGroupIdName;
    //作废日期
    private String CancelDate;
    //仓管员
    private String StockerIdName;
    //作废状态
    private String CancelStatus;
    //采购组织
    private String PurchaseOrgIdName;
    //单据状态
    private String DocumentStatus;
    //采购部门
    private String PurchaseDeptIdName;
    //需求组织
    private String DemandOrgIdName;
    //采购员
    private String PurchaserIdName;
    //组织间结算跨法人标识
    private String IsInterLegalPerson;
    //确认日期
    private String ConfirmDate;
    //确认状态
    private String ConfirmStatus;
    //拆单类型
    private String SplitBillType;
    //Entries(InStockEntry)
    private String InStockEntry;
    //是否正常获取数据备注
    private String ResultRemark;
}
