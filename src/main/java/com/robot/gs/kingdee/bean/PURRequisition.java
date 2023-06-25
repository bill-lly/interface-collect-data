package com.robot.gs.kingdee.bean;

import lombok.Data;

//采购申请
@Data
public class PURRequisition {
    //实体主键
    private Long Id;
    //单据类型
    private String BillTypeIDIdName;
    //创建人
    private String CreatorIdName;
    //单据编号
    private String BillNo;
    //创建日期
    private String CreateDate;
    //最后修改人
    private String ModifierIdName;
    //最后修改日期
    private String ModifyDate;
    //申请类型
    private String RequestType;
    //数据状态
    private String DocumentStatus;
    //关闭状态
    private String CloseStatus;
    //申请组织
    private String ApplicationOrgIdName;
    //审核人
    private String FApprovePersonName;
    //审核日期
    private String ApproveDate;
    //申请人
    private String ApplicantIdName;
    //关闭人
    private String CloserIdName;
    //申请部门
    private String ApplicationDeptIdName;
    //关闭日期
    private String CloseDate;
    //币别
    private String CurrencyIdName;
    //含税金额合计
    private Double TotalAmount;
    //作废人
    private String CancellerIdName;
    //作废日期
    private String CancelDate;
    //合并作废
    private String IsMergeCancel;
    //手工关闭
    private String ManualClose;
    //备注
    private String Note;
    //申请日期
    private String ApplicationDate;
    //作废状态
    private String CancelStatus;
    //本部门终审人
    private String FinApprovePersonName;
    //MRP运算号
    private String FMRPNo;
    //收货地址
    private String FOraCombo;
    //ReqEntry
    private String ReqEntryList;
    //是否正常获取数据备注
    private String ResultRemark;

}
