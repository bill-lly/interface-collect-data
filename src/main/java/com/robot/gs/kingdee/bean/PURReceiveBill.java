package com.robot.gs.kingdee.bean;

import lombok.Data;

//收料通知单字段
@Data
public class PURReceiveBill {
    //实体主键
    private Long Id;
    //单据类型
    private String BillTypeID;
    //创建人
    private String CreatorIdName;
    //供货方
    private String SupplyIdName;
    //创建日期
    private String CreateDate;
    //结算方
    private String SFSettleIdName;
    //业务类型
    private String BusinessType;
    //单据编号
    private String BillNo;
    //最后修改人
    private String ModifierIdName;
    //收款方
    private String SFChargeIdName;
    //收料日期
    private String Date;
    //最后修改日期
    private String ModifyDate;
    //供应商
    private String SupplierIdName;
    //审核人
    private String ApproverIdName;
    //收料组织
    private String StockOrgIdName;
    //审核日期
    private String ApproveDate;
    //收料部门
    private String ReceiveDeptIdName;
    //作废人
    private String CancellerId;
    //作废日期
    private String CancelDate;
    //收料员
    private String ReceiverIdName;
    //作废状态
    private String CancelStatus;
    //采购组织
    private String PurOrgIdName;
    //单据状态
    private String DocumentStatus;
    //货主类型
    private String OwnerTypeIdHead;
    //货主
    private String OwnerIdHeadName;
    //采购部门
    private String PurDeptIdName;
    //需求组织
    private String DemandOrgIdName;
    //采购组
    private String PurGroupId;
    //采购员
    private String PurchaserIdName;
    //备注
    private String Note;
    //确认状态
    private String ConfirmStatus;
    //验收方式
    private String AccType;
    //Entries(PURReceiveEntry)
    private String PURReceiveEntry;
    //是否正常获取数据备注
    private String ResultRemark;
}
