package com.robot.gs.kingdee.bean;

import lombok.Data;

//销售退货
@Data
public class SALReturnstock {
    //实体主键
    private Long Id;
    //单据类型
    private String BillTypeIDName;
    //日期
    private String FDate;
    //单据编号
    private String BillNo;
    //单据状态
    private String DocumentStatus;
    //销售组织
    private String SaleOrgIdName;
    //销售部门
    private String SledeptidName;
    //退货客户
    private String RetcustIdName;
    //退货原因
    private String ReturnReason;
    //交货地点
    private String HeadLocId;
    //销售组
    private String SaleGroupIdName;
    //销售员
    private String SalesManIdName;
    //库存组织
    private String StockOrgIdName;
    //库存部门
    private String StockDeptIdName;
    //库存组
    private String StockerGroupIdName;
    //仓管员
    private String StockerIdName;
    //业务类型
    private String BussinessType;
    //收货方
    private String ReceiveCustIdName;
    //收货人姓名
    private String FLinkMan;
    //收货方地址
    private String ReceiveAddress;
    //收货方联系人
    private String ReceiveCusContact;
    //结算方
    private String SettleCustIdName;
    //联系电话
    private String FLinkPhone;
    //付款方
    private String PayCustIdName;
    //对应组织
    private String CorrespondOrgId;
    //货主类型
    private String OwnerTypeIdHead;
    //货主
    private String OwnerIdHeadName;
    //创建人
    private String CreatorIdName;
    //创建日期
    private String CreateDate;
    //最后修改人
    private String ModifierIdName;
    //最后修改日期
    private String ModifyDate;
    //审核人
    private String ApproverIdName;
    //审核日期
    private String ApproveDate;
    //作废状态
    private String CancelStatus;
    //作废人
    private String CancellerIdName;
    //作废日期
    private String CancelDate;
    //整单服务标识
    private String IsTotalServiceOrCost;
    //跨组织结算生成
    private String FISGENFORIOS;
    //Entries
    private String SALReturnstockEntries;
    //是否正常获取数据备注
    private String ResultRemark;
}
