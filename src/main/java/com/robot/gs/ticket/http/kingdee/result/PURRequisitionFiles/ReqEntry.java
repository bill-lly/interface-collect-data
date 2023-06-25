package com.robot.gs.ticket.http.kingdee.result.PURRequisitionFiles;

import lombok.Data;

@Data
public class ReqEntry {
    //EntryId
    private Long EntryId;
    //物料编码
    private String MaterialIdNumber;
    //物料名称
    private String MaterialIdName;
    //物料类别
    private String MaterialGroupName;
    //物料说明
    private String MaterialDesc;
    //规格型号
    private String FoldModel;
    //辅助属性
    private String MaterialAuxPty;
    //申请单位
    private String UnitIdName;
    //申请数量
    private Double Qty;
    //申请数量（基本单位）
    private Double BaseReqQty;
    //批准数量
    private Double ApproveQty;
    //到货日期
    private String ArrivalDate;
    //提前期
    private String LeadTime;
    //采购组织
    private String PurchaseOrgIdName;
    //建议供应商
    private String SuggestSupplierIdName;
    //备注
    private String EntryNote;
    //计价单位
    private String PriceUnitIdName;
    //单价
    private Double EvaluatePrice;
    //计价数量
    private Double PriceUnitQty;
    //含税金额
    private Double ReqAmount;
    //需求组织
    private String RequireOrgIdName;
    //订单数量
    private Double OrderQty;
    //剩余数量
    private Double RemainQty;
    //入库数量
    private Double StockQty;
    //收料数量
    private Double ReceiveQty;
    //终止日期
    private String TerminateDate;
    //批准数量(基本单位)
    private Double FBaseUnitQty;
    //业务关闭
    private String MRPCloseStatus;
    //计划确认
    private String PlanConfirm;
    //需求跟踪号
    private String ReqTraceNo;
    //含税单价
    private Double TaxPrice;
    //VMI业务
    private String IsVmiBusiness;
    //产品类型
    private String RowType;
    //研发项目
    private String FResearchProject;
    //所属采购员
    private String PurchaserIdName;
    //采购链接
    private String FGsPlink;
    //旧物料编码
    private String MaterialIdOldNumber;
}
