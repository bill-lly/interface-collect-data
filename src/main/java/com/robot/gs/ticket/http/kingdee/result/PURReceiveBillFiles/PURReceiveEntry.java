package com.robot.gs.ticket.http.kingdee.result.PURReceiveBillFiles;

import lombok.Data;

@Data
public class PURReceiveEntry {
    //EntryId(PURReceiveEntry)
    private Long EntryId;
    //物料编码
    private String MaterialIDNumber;
    //物料名称
    private String MaterialIDName;
    //需求人
    private String DemanderIdName;
    //物料说明
    private String MaterialDesc;
    //规格型号
    private String Specification;
    //物料类别
    private String MaterialGroup;
    //控制入库数量
    private String CtrlStockInPercent;
    //合格入库关联数量
    private Double InStockJoinQty;
    //收料单位
    private String UnitIdName;
    //入库数量
    private Double InStockQty;
    //实收数量
    private Double MustQty;
    //预计到货日期
    private String PreDeliveryDate;
    //实到数量
    private Double ActLandQty;
    //采购基本数量
    private Double BaseUnitQty;
    //交货数量
    private Double ActReceiveQty;
    //退料数量
    private Double ReturnQty;
    //拒收数量
    private Double RejectQty;
    //基本单位
    private String BaseUnitIdName;
    //仓库
    private String StockID;
    //源单内码
    private String SrcId;
    //备注.1
    private String Description;
    //仓位
    private String StockLocId;
    //库存状态
    private String StockStatusIdName;
    //源单类型
    private String SrcFormId;
    //源单分录内码
    private String FDetailEntityLinkSId;
    //源单单号
    private String SrcBillNo;
    //订单单号
    private String OrderBillNo;
    //计价数量
    private Double PriceUnitQty;
    //含税单价
    private Double TaxPrice;
    //是否赠品
    private String GiveAway;
    //费用项目
    private String ChargeProjectID;
    //价税合计(本位币)
    private Double AllAmountLC;
    //紧急放行
    private String EmergencyRelease;
    //检验关联数量
    private Double CheckJoinQty;
    //合格数量
    private Double ReceiveQty;
    //判退数量
    private Double RefuseQty;
    //样本破坏数量
    private Double SampleDamageQty;
    //来料检验
    private String CheckIncoming;
    //卡片关联数量
    private Double CardJoinQty;
    //卡片数量
    private Double CardQty;
    //检验数量
    private Double CheckQty;
    //让步接收数量(基本单位)
    private Double CsnReceiveBaseQty;
    //让步接收数量
    private Double CsnReceiveQty;
    //不合格关联数量(基本单位)
    private Double RefuseJoinBaseQty;
    //让步接收关联数量(基本单位)
    private Double CsnReceiveJoinBaseQty;
    //计价基本数量
    private Double PriceBaseQty;
    //定价单位
    private String SetPriceUnitID;
    //库存单位
    private String StockUnitIDName;
    //合格入库库存关联数量(基本单位)
    private Double StockJoinBaseQTY;
    //退料库存关联数量(基本单位)
    private Double ReturnStkJNBaseQty;
    //关联应付数量(库存基本)
    private Double StockBaseAPJoinQty;
    //供应商交货数量
    private Double SupDelQty;
    //执行人
    private String ExecuteId;
    //产品类型
    private String RowType;
    //研发项目
    private String FResearchProject;
    //验收比例%
    private Double AccRate;
    //订单数量
    private Double FPoQty;
    //订单规格型号
    private String FoldModel;
}
