package com.robot.gs.ticket.http.kingdee.result.BDMaterialFiles;

import lombok.Data;

@Data
public class MaterialBase {
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
}
