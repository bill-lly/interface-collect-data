package com.robot.gs.kingdee.bean;

import lombok.Data;

//销售出库
@Data
public class SALOutstock {
    //实体主键
    private Long Id;
    //日期
    private String FDate;
    //单据编号
    private String BillNo;
    //客户
    private String CustomerIDName;
    //发货组织
    private String StockOrgIdName;
    //单据状态
    private String DocumentStatus;
    //单据类型
    private String BillTypeIDName;
    //销售组织
    private String SaleOrgIdName;
    //销售部门
    private String SaleDeptIDName;
    //销售员
    private String SalesManIDName;
    //业务类型
    private String BussinessType;
    //收货方
    private String ReceiverIDName;
    //结算方
    private String SettleIDName;
    //付款方
    private String PayerIDName;
    //跨组织业务类型
    private String TransferBizType;
    //创建人
    private String FCreatorIdName;
    //创建日期
    private String FCreateDate;
    //最后修改日期
    private String FModifyDate;
    //审核人
    private String ApproverIDName;
    //最后修改人
    private String FModifierIdName;
    //审核日期
    private String ApproveDate;
    //作废状态
    private String CancelStatus;
    //作废日期
    private String CancelDate;
    //货主
    private String OwnerIdHeadName;
    //销售合同编号
    private String FSaleContractNo;
    //销售收入类别
    private String FIncomeType;
    //送货地址
    private String FReceiveAddress;
    //销售区域
    private String FGSSalesReigon;
    //销售订单
    private String FGsSo;
    //跨组织结算生成
    private String FISGENFORIOS;
    //Entries
    private String SALOutstockEntries;
    //是否正常获取数据备注
    private String ResultRemark;
}
