package com.robot.gs.ticket.http.kingdee.result.PURPurchaseOrderFiles;

import lombok.Data;

@Data
public class POOrderEntry {
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
    //采购单位
    private String UnitIdName;
    //采购数量
    private Double Qty;
    //交货日期
    private String DeliveryDate;
    //金额
    private Double Amount;
    //需求组织
    private String RequireOrgIdName;
    //费用项目
    private String ChargeProjectID;
    //需求人
    private String RequireStaffIdName;
    //收料组织
    private String ReceiveOrgIdName;
    //备注
    private String Note;
    //最早交货日期
    private String DeliveryEarlyDate;
    //最晚交货日期
    private String DeliveryLastDate;
    //冻结日期
    private String FreezeDate;
    //终止日期
    private String TerminateDate;
    //累计收料数量
    private Double ReceiveQty;
    //剩余收料数量
    private Double RemainReceiveQty;
    //累计入库数量
    private Double StockInQty;
    //剩余入库数量
    private Double RemainStockInQty;
    //累计退料数量
    private Double MrbQty;
    //源单类型
    private String SrcBillTypeId;
    //源单编号
    private String SrcBillNo;
    //需求来源
    private String DemandType;
    //需求单据编号
    private String DemandBillNo;
    //需求单据行号
    private Integer DemandBillEntrySeq;
    //计划确认
    private String PlanConfirm;
    //研发项目
    private String FResearchProject;
    //采购申请行号
    private String FGsPrItem;
}
