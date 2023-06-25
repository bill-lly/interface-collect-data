package com.robot.gs.kingdee.bean;

import lombok.Data;

//物料列表
@Data
public class BDMaterial {
    //实体主键
    private Long Id;
    //创建组织
    private String CreateOrgIdName;
    //物料内码
    private String FOraInt;
    //使用组织
    private String UseOrgIdName;
    //编码
    private String Number;
    //物料来源
    private String MaterialSRC;
    //名称
    private String Name;
    //规格型号
    private String Specification;
    //助记码
    private String MnemonicCode;
    //旧物料编码
    private String OldNumber;
    //物料分组
    private String MaterialGroupName;
    //物料分组编码
    private String MaterialGroupNumber;
    //数据状态
    private String DocumentStatus;
    //禁用状态
    private String ForbidStatus;
    //创建人
    private String CreatorIdName;
    //创建日期
    private String CreateDate;
    //修改人
    private String ModifierIdName;
    //修改日期
    private String ModifyDate;
    //审核人
    private String ApproverIdName;
    //审核日期
    private String ApproveDate;
    //禁用人
    private String ForbidderId;
    //禁用日期
    private String ForbidDate;
    //图片存储类型
    private String ImgStorageType;
    //图片(数据库)
    private String Image;
    //按批号匹配供需
    private String DSMatchByLot;
    //图片(文件服务器)
    private String ImageFileServer;
    //禁用原因
    private String ForbidReason;
    //已使用
    private String RefStatus;
    //开票名称
    private String FOraText;
    //开票规格
    private String FOraText1;
    //生命周期
    private String FOraCombo;
    //PSW
    private String FPATCCombo;
    //EntryId(MaterialBase)
    private Long EntryIdMB;
    //物料属性
    private String ErpClsID;
    //存货类别
    private String CategoryIDName;
    //税分类
    private String TaxType;
    //默认税率
    private String TaxRateIdName;
    //基本单位
    private String BaseUnitIdName;
    //允许采购
    private String IsPurchase;
    //允许库存
    private String IsInventory;
    //允许委外
    private String IsSubContract;
    //允许销售
    private String IsSale;
    //允许生产
    private String IsProduce;
    //允许转资产
    private String IsAsset;
    //条码
    private String BarCode;
    //毛重
    private Double GrossWeight;
    //净重
    private Double NetWeight;
    //尺寸单位
    private String VolumeUnitIdName;
    //长
    private Double Length;
    //宽
    private Double Width;
    //高
    private Double Height;
    //重量单位
    private String WeightUnitIdName;
    //体积
    private Double Volume;
    //配置生产
    private String ConfigType;
    //特征件子项
    private String FeatureItem;
    //套件
    private String Suite;
    //结算成本价加减价比例(%)
    private Double CostPriceRate;
    //是否变更中
    private String IsChange;
    //EntryId(MaterialStock)
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
    //是否正常获取数据备注
    private String ResultRemark;
}
