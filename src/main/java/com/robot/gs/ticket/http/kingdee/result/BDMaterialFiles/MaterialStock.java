package com.robot.gs.ticket.http.kingdee.result.BDMaterialFiles;

import lombok.Data;

@Data
public class MaterialStock {
    //EntryId
    private Long EntryIdMS;
    //库存单位
    private String StoreUnitIDName;
    //辅助单位
    private String AuxUnitID;
    //仓库
    private String StockId;
    //仓位
    private String StockPlaceId;
    //仓位.生产物料仓仓位.编码
    private String SpProdMtrlNumber;
    //仓位.生产物料仓仓位.名称
    private String SpProdMtrlName;
    //仓位.售后物料总仓仓位.编码
    private String SpSalMtrlNumber;
    //仓位.售后物料总仓仓位.名称
    private String SpSalMtrlName;
    //仓位.样机租赁仓仓位.编码
    private String SpPttRentNumber;
    //仓位.样机租赁仓仓位.名称
    private String SpPttRentName;
    //仓位.张家港不良品仓.编码
    private String SpZjgNpftNumber;
    //仓位.张家港不良品仓.名称
    private String SpZjgNpftName;
    //仓位.研发测试虚拟仓仓位.编码
    private String SpRTVirtualNumber;
    //仓位.研发测试虚拟仓仓位.名称
    private String SpRTVirtualName;
    //仓位.控制盒仓仓位.编码
    private String SpCtrlBoxNumber;
    //仓位.控制盒仓仓位.名称
    private String SpCtrlBoxName;
    //仓位.张家港研发物料仓.编码
    private String SpZjgRsMtrlNumber;
    //仓位.张家港研发物料仓.名称
    private String SpZjgRsMtrlName;
    //仓位.工具仓.编码
    private String SpToolsNumber;
    //仓位.工具仓.名称
    private String SpToolsName;
    //仓位.张家港质量测试仓.编码
    private String SpZjgQltTestNumber;
    //仓位.张家港质量测试仓.名称
    private String SpZjgQltTestName;
    //仓位.园区仓位.编码
    private String SpParkNumber;
    //仓位.园区仓位.名称
    private String SpParkName;
    //可锁库
    private String IsLockStock;
    //启用盘点周期
    private String IsCycleCounting;
    //盘点周期单位
    private String CountCycle;
    //盘点周期
    private Long CountDay;
    //必盘
    private String IsMustCounting;
    //启用批号管理
    private String IsBatchManage;
    //批号编码规则
    private String BatchRuleID;
    //启用保质期管理
    private String IsKFPeriod;
    //批号附属信息
    private String IsExpParToFlot;
    //保质期单位
    private String ExpUnit;
    //保质期
    private Long ExpPeriod;
    //在架寿命期
    private Long OnlineLife;
    //参考成本
    private String RefCost;
    //币别
    private String CurrencyIdName;
    //库存管理
    private String IsSNManage;
    //启用最小库存
    private String IsEnableMinStock;
    //启用安全库存
    private String IsEnableSafeStock;
    //序列号编码规则
    private String SNCodeRule;
    //启用再订货点
    private String IsEnableReOrder;
    //启用最大库存
    private String IsEnableMaxStock;
    //序列号单位
    private String SNUnit;
    //最小库存
    private Double MinStock;
    //库存安全库存
    private Double SafeStock;
    //再订货点
    private Double ReOrderGood;
    //经济订货批量
    private Double EconReOrderQty;
    //最大库存
    private Double MaxStock;
    //生产追溯
    private String IsSNPRDTracy;
    //单箱标准数量
    private Double BoxStandardQty;
}
