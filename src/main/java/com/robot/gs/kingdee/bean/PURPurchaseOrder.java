package com.robot.gs.kingdee.bean;

import lombok.Data;

//采购订单表
@Data
public class PURPurchaseOrder {
    //实体主键
    private Long Id;
    //单据类型
    private String BillTypeIdName;
    //业务类型
    private String BusinessType;
    //单据编号
    private String BillNo;
    //采购日期
    private String Date;
    //供应商
    private String SupplierIdName;
    //单据状态
    private String DocumentStatus;
    //采购组织
    private String PurchaseOrgIdName;
    //采购员
    private String PurchaserIdName;
    //手工关闭
    private String ManualClose;
    //创建人
    private String CreatorIdName;
    //创建日期
    private String CreateDate;
    //最后修改日期
    private String ModifyDate;
    //审核日期
    private String ApproveDate;
    //关闭状态
    private String CloseStatus;
    //关闭日期
    private String CloseDate;
    //作废状态
    private String CancelStatus;
    //作废日期
    private String CancelDate;
    //确认状态
    private String ConfirmStatus;
    //确认日期
    private String ConfirmDate;
    //MRP运算号
    private String FMRPNo;
    //采购申请号
    private String FGsPr;
    //收货地址
    private String FOraCombo;
    //POOrderEntry
    private String pOOrderEntry;
    //是否正常获取数据备注
    private String ResultRemark;

}
