package com.robot.gs.kingdee.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.kingdee.bean.BDMaterial;
import com.robot.gs.ticket.http.kingdee.result.BDMaterialFiles.MaterialBase;
import com.robot.gs.ticket.http.kingdee.result.BDMaterialFiles.MaterialStock;
import com.robot.gs.ticket.http.kingdee.result.PURRequisitionFiles.ReqEntry;
import com.robot.gs.udesk.integration.DataConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class BDMaterialConverter extends KingdeeDataConverter<BDMaterial> {
    public BDMaterial from(String data) {
        BDMaterial bdMaterial = new BDMaterial();
        try {
            map(bdMaterial, data);
        } catch (RuntimeException e) {
            log.error("获取" + BDMaterial.class.getName() + "的result失败");
            log.error("data：" + data);
        }
        return bdMaterial;
    }

    private void map(BDMaterial bdMaterial, String data) {
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        JSONObject jsonObject = (JSONObject.parseObject(data).getJSONObject("Result")).getJSONObject("Result");
        //实体主键
        bdMaterial.setId(jsonObject.getLong("Id"));
        //创建组织
        bdMaterial.setCreateOrgIdName(value(getListValue("CreateOrgId", "Name", jsonObject)));
        //物料内码
        bdMaterial.setFOraInt(value(jsonObject.getString("F_ora_Int")));
        //使用组织
        bdMaterial.setUseOrgIdName(value(getListValue("UseOrgId", "Name", jsonObject)));
        //编码
        bdMaterial.setNumber(value(jsonObject.getString("Number")));
        //物料来源
        bdMaterial.setMaterialSRC(value(jsonObject.getString("MaterialSRC")));
        //名称
        bdMaterial.setName(value(getSimListValue("Name", jsonObject)));
        //规格型号
        bdMaterial.setSpecification(value(getSimListValue("Specification", jsonObject)).replaceAll("\"", "\\?").replaceAll("\\\\","/"));
        //助记码
        bdMaterial.setMnemonicCode(value(jsonObject.getString("MnemonicCode")));
        //旧物料编码
        bdMaterial.setOldNumber(value(jsonObject.getString("OldNumber")));
        //物料分组
        bdMaterial.setMaterialGroupName(value(getListValue("MaterialGroup", "Name", jsonObject)));
        //物料分组编码
        bdMaterial.setMaterialGroupNumber(value(getJsonValue("MaterialGroup", "Number", jsonObject)));
        //数据状态
        String documentStatus = "";
        if (jsonObject.getString("DocumentStatus") != null) {
            switch (jsonObject.getString("DocumentStatus")) {
                case "Z":
                    documentStatus = "暂存";
                    break;
                case "A":
                    documentStatus = "创建";
                    break;
                case "B":
                    documentStatus = "审核中";
                    break;
                case "C":
                    documentStatus = "已审核";
                    break;
                case "D":
                    documentStatus = "重新审核";
                    break;
                default:
                    documentStatus = null;
                    break;
            }
        }
        bdMaterial.setDocumentStatus(value(documentStatus));
        //禁用状态
        String forbidStatus = "";
        if (jsonObject.getString("ForbidStatus") != null) {
            switch (jsonObject.getString("ForbidStatus")) {
                case "A":
                    forbidStatus = "否";
                    break;
                case "B":
                    forbidStatus = "是";
                    break;
                default:
                    forbidStatus = null;
                    break;
            }
        }
        bdMaterial.setForbidStatus(value(forbidStatus));
        //创建人
        bdMaterial.setCreatorIdName(value(getJsonValue("CreatorId", "Name", jsonObject)));
        //创建日期
        bdMaterial.setCreateDate(value(jsonObject.getString("CreateDate")));
        //修改人
        bdMaterial.setModifierIdName(value(getJsonValue("ModifierId", "Name", jsonObject)));
        //修改日期
        bdMaterial.setModifyDate(value(jsonObject.getString("ModifyDate")));
        //审核人
        bdMaterial.setApproverIdName(value(getJsonValue("ApproverId", "Name", jsonObject)));
        //审核日期
        bdMaterial.setApproveDate(value(jsonObject.getString("ApproveDate")));
        //禁用人
        bdMaterial.setForbidderId(value(getJsonValue("ForbidderId", "Name", jsonObject)));
        //禁用日期
        bdMaterial.setForbidDate(value(jsonObject.getString("ForbidDate")));
        //图片存储类型
        bdMaterial.setImgStorageType(value(jsonObject.getString("ImgStorageType")));
        //图片(数据库)
        bdMaterial.setImage(value(jsonObject.getString("Image")));
        //按批号匹配供需
        bdMaterial.setDSMatchByLot(value(jsonObject.getString("DSMatchByLot")));
        //图片(文件服务器)
        bdMaterial.setImageFileServer(value(jsonObject.getString("ImageFileServer")));
        //禁用原因
        bdMaterial.setForbidReason(value(getSimListValue("FORBIDREASON", jsonObject)));
        //已使用
        String refStatus = "";
        if (jsonObject.getString("RefStatus") != null) {
            switch (jsonObject.getString("RefStatus")) {
                case "0":
                    refStatus = "否";
                    break;
                case "1":
                    refStatus = "是";
                    break;
                default:
                    refStatus = null;
                    break;
            }
        }
        bdMaterial.setRefStatus(value(refStatus));
        //开票名称
        bdMaterial.setFOraText(value(jsonObject.getString("F_ora_Text")));
        //开票规格
        bdMaterial.setFOraText1(value(jsonObject.getString("F_ora_Text1")));
        //生命周期
        String foraCombo = "";
        if (jsonObject.getString("F_ora_Combo") != null) {
            switch (jsonObject.getString("F_ora_Combo")) {
                case "2":
                    foraCombo = "正式发布";
                    break;
                case "4":
                    foraCombo = "禁用";
                    break;
                case "3":
                    foraCombo = "慎用";
                    break;
                case "1":
                    foraCombo = "预发布";
                    break;
                default:
                    foraCombo = null;
                    break;
            }
        }
        bdMaterial.setFOraCombo(value(foraCombo));
        //PSW
        bdMaterial.setFPATCCombo(value(jsonObject.getString("F_PATC_Combo")));
        //MaterialBase
        List<JSONObject> materialBases = jsonObject.getList("MaterialBase", JSONObject.class) != null ? jsonObject.getList("MaterialBase", JSONObject.class) : new ArrayList<>();
//
//        List<MaterialBase> materialBaseList = new ArrayList<>();
//        for (JSONObject materialBase : materialBases) {
//            MaterialBase mb = new MaterialBase();
//            setMaterialBaseValues(mb, materialBase);
//            materialBaseList.add(mb);
//        }
//        String gsonStrCl1 = gson.toJson(materialBaseList);
//        bdMaterial.setMaterialBase(value(gsonStrCl1));
        setMaterialBaseValues(bdMaterial, materialBases.size() > 0 ? materialBases.get(0) : null);

        //MaterialStock
        List<JSONObject> materialStocks = jsonObject.getList("MaterialStock", JSONObject.class) != null ? jsonObject.getList("MaterialStock", JSONObject.class) : new ArrayList<>();

//        List<MaterialStock> materialStockList = new ArrayList<>();
//        for (JSONObject materialStock : materialStocks) {
//            MaterialStock ms = new MaterialStock();
//            setMaterialStockValues(ms, materialStock);
//            materialStockList.add(ms);
//        }
//        String gsonStrCl2 = gson.toJson(materialStockList);
//        bdMaterial.setMaterialStock(value(gsonStrCl2));

        setMaterialStockValues(bdMaterial, materialStocks.size() > 0 ? materialStocks.get(0) : null);
        //是否正常获取数据备注
        bdMaterial.setResultRemark(jsonObject.getLong("Id") == null ? value(jsonObject.getString("ResultRemark")) : "正常获取数据");

    }

    //private void setMaterialBaseValues(MaterialBase materialBase, JSONObject materialBaseJSON) {
    private void setMaterialBaseValues(BDMaterial materialBase, JSONObject materialBaseJSON) {
        if (materialBaseJSON == null) {
            return;
        }
        //修复字段中value含有"导致Json解析失败，替换为?
        //修复字段value含有\导致Json解析失败，替换为/
        //EntryId(MaterialBase)
        materialBase.setEntryIdMB(materialBaseJSON.getLong("Id"));
        //物料属性
        String erpClsID = "";
        if (materialBaseJSON.getString("ErpClsID") != null) {
            switch (materialBaseJSON.getString("ErpClsID")) {
                case "10":
                    erpClsID = "资产";
                    break;
                case "9":
                    erpClsID = "配置";
                    break;
                case "2":
                    erpClsID = "自制";
                    break;
                case "11":
                    erpClsID = "费用";
                    break;
                case "5":
                    erpClsID = "虚拟";
                    break;
                case "7":
                    erpClsID = "一次性";
                    break;
                case "13":
                    erpClsID = "产品系列";
                    break;
                case "12":
                    erpClsID = "模型";
                    break;
                case "3":
                    erpClsID = "委外";
                    break;
                case "4":
                    erpClsID = "特征";
                    break;
                case "6":
                    erpClsID = "服务";
                    break;
                case "1":
                    erpClsID = "外购";
                    break;
                default:
                    erpClsID = null;
                    break;
            }
        }
        materialBase.setErpClsID(value(erpClsID));
        //存货类别
        materialBase.setCategoryIDName(value(getListValue("CategoryID", "Name", materialBaseJSON)));
        //税分类
        materialBase.setTaxType(value(getListValue("TaxType", "FDataValue", materialBaseJSON)));
        //默认税率
        materialBase.setTaxRateIdName(value(getListValue("TaxRateId", "Name", materialBaseJSON)));
        //基本单位
        materialBase.setBaseUnitIdName(value(getListValue("BaseUnitId", "Name", materialBaseJSON)));
        //允许采购
        materialBase.setIsPurchase(value(materialBaseJSON.getString("IsPurchase")));
        //允许库存
        materialBase.setIsInventory(value(materialBaseJSON.getString("IsInventory")));
        //允许委外
        materialBase.setIsSubContract(value(materialBaseJSON.getString("IsSubContract")));
        //允许销售
        materialBase.setIsSale(value(materialBaseJSON.getString("IsSale")));
        //允许生产
        materialBase.setIsProduce(value(materialBaseJSON.getString("IsProduce")));
        //允许转资产
        materialBase.setIsAsset(value(materialBaseJSON.getString("IsAsset")));
        //条码
        materialBase.setBarCode(value(materialBaseJSON.getString("BARCODE")));
        //毛重
        materialBase.setGrossWeight(value(materialBaseJSON.getDouble("GROSSWEIGHT"), 0.0));
        //净重
        materialBase.setNetWeight(value(materialBaseJSON.getDouble("NETWEIGHT"), 0.0));
        //尺寸单位
        materialBase.setVolumeUnitIdName(value(getListValue("VOLUMEUNITID", "Name", materialBaseJSON)));
        //长
        materialBase.setLength(value(materialBaseJSON.getDouble("LENGTH"), 0.0));
        //宽
        materialBase.setWidth(value(materialBaseJSON.getDouble("WIDTH"), 0.0));
        //高
        materialBase.setHeight(value(materialBaseJSON.getDouble("HEIGHT"), 0.0));
        //重量单位
        materialBase.setWeightUnitIdName(value(getListValue("WEIGHTUNITID", "Name", materialBaseJSON)));
        //体积
        materialBase.setVolume(value(materialBaseJSON.getDouble("VOLUME"), 0.0));
        //配置生产
        materialBase.setConfigType(value(materialBaseJSON.getString("CONFIGTYPE")));
        //特征件子项
        materialBase.setFeatureItem(value(materialBaseJSON.getString("FeatureItem")));
        //套件
        materialBase.setSuite(value(materialBaseJSON.getString("Suite")));
        //结算成本价加减价比例(%)
        materialBase.setCostPriceRate(value(materialBaseJSON.getDouble("CostPriceRate"), 0.0));
        //是否变更中
        materialBase.setIsChange(value(materialBaseJSON.getString("IsChange")));
    }

    //private void setMaterialStockValues(MaterialStock materialStock, JSONObject materialStockJSON) {
    private void setMaterialStockValues(BDMaterial materialStock, JSONObject materialStockJSON) {
        if (materialStockJSON == null) {
            return;
        }
        //EntryId(MaterialStock)
        materialStock.setEntryIdMS(materialStockJSON.getLong("Id"));
        //库存单位
        materialStock.setStoreUnitIDName(value(getListValue("StoreUnitID", "Name", materialStockJSON)));
        //辅助单位
        materialStock.setAuxUnitID(value(materialStockJSON.getString("AuxUnitID")));
        //仓库
        materialStock.setStockId(value(getListValue("StockId", "Name", materialStockJSON)));
        //仓位
        JSONObject stockPlaceId = materialStockJSON.getJSONObject("StockPlaceId");
        materialStock.setStockPlaceId(value(stockPlaceId != null ? getListValue("F100003", "Name", stockPlaceId) : null));
        //仓位_生产物料仓仓位_编码
        materialStock.setSpProdMtrlNumber(value(getSpNumber("StockPlaceId", "F100003", "Number", materialStockJSON)));
        //仓位_生产物料仓仓位_名称
        materialStock.setSpProdMtrlName(value(stockPlaceId != null ? getListValue("F100003", "Name", stockPlaceId) : null));
        //仓位_售后物料总仓仓位_编码
        materialStock.setSpSalMtrlNumber(value(getSpNumber("StockPlaceId", "F100004", "Number", materialStockJSON)));
        //仓位_售后物料总仓仓位_名称
        materialStock.setSpSalMtrlName(value(stockPlaceId != null ? getListValue("F100004", "Name", stockPlaceId) : null));
        //仓位_样机租赁仓仓位_编码
        materialStock.setSpPttRentNumber(value(getSpNumber("StockPlaceId", "F100005", "Number", materialStockJSON)));
        //仓位_样机租赁仓仓位_名称
        materialStock.setSpPttRentName(value(stockPlaceId != null ? getListValue("F100005", "Name", stockPlaceId) : null));
        //仓位_张家港不良品仓_编码
        materialStock.setSpZjgNpftNumber(value(getSpNumber("StockPlaceId", "F100006", "Number", materialStockJSON)));
        //仓位_张家港不良品仓_名称
        materialStock.setSpZjgNpftName(value(stockPlaceId != null ? getListValue("F100006", "Name", stockPlaceId) : null));
        //仓位_研发测试虚拟仓仓位_编码
        materialStock.setSpRTVirtualNumber(value(getSpNumber("StockPlaceId", "F100007", "Number", materialStockJSON)));
        //仓位_研发测试虚拟仓仓位_名称
        materialStock.setSpRTVirtualName(value(stockPlaceId != null ? getListValue("F100007", "Name", stockPlaceId) : null));
        //仓位_控制盒仓仓位_编码
        materialStock.setSpCtrlBoxNumber(value(getSpNumber("StockPlaceId", "F100008", "Number", materialStockJSON)));
        //仓位_控制盒仓仓位_名称
        materialStock.setSpCtrlBoxName(value(stockPlaceId != null ? getListValue("F100008", "Name", stockPlaceId) : null));
        //仓位_张家港研发物料仓_编码
        materialStock.setSpZjgRsMtrlNumber(value(getSpNumber("StockPlaceId", "F100009", "Number", materialStockJSON)));
        //仓位_张家港研发物料仓_名称
        materialStock.setSpZjgRsMtrlName(value(stockPlaceId != null ? getListValue("F100009", "Name", stockPlaceId) : null));
        //仓位_工具仓_编码
        materialStock.setSpToolsNumber(value(getSpNumber("StockPlaceId", "F100010", "Number", materialStockJSON)));
        //仓位_工具仓_名称
        materialStock.setSpToolsName(value(stockPlaceId != null ? getListValue("F100010", "Name", stockPlaceId) : null));
        //仓位_张家港质量测试仓_编码
        materialStock.setSpZjgQltTestNumber(value(getSpNumber("StockPlaceId", "F100011", "Number", materialStockJSON)));
        //仓位_张家港质量测试仓_名称
        materialStock.setSpZjgQltTestName(value(stockPlaceId != null ? getListValue("F100011", "Name", stockPlaceId) : null));
        //仓位_园区仓位_编码
        materialStock.setSpParkNumber(value(getSpNumber("StockPlaceId", "F100012", "Number", materialStockJSON)));
        //仓位_园区仓位_名称
        materialStock.setSpParkName(value(stockPlaceId != null ? getListValue("F100012", "Name", stockPlaceId) : null));
        //可锁库
        materialStock.setIsLockStock(value(materialStockJSON.getString("IsLockStock")));
        //启用盘点周期
        materialStock.setIsCycleCounting(value(materialStockJSON.getString("IsCycleCounting")));
        //盘点周期单位
        materialStock.setCountCycle(value(materialStockJSON.getString("CountCycle")));
        //盘点周期
        materialStock.setCountDay(materialStockJSON.getLong("CountDay") != null ? materialStockJSON.getLong("CountDay") : 0);
        //必盘
        materialStock.setIsMustCounting(value(materialStockJSON.getString("IsMustCounting")));
        //启用批号管理
        materialStock.setIsBatchManage(value(materialStockJSON.getString("IsBatchManage")));
        //批号编码规则
        materialStock.setBatchRuleID(value(materialStockJSON.getString("BatchRuleID")));
        //启用保质期管理
        materialStock.setIsKFPeriod(value(materialStockJSON.getString("IsKFPeriod")));
        //批号附属信息
        materialStock.setIsExpParToFlot(value(materialStockJSON.getString("IsExpParToFlot")));
        //保质期单位
        materialStock.setExpUnit(value(materialStockJSON.getString("ExpUnit")));
        //保质期
        materialStock.setExpPeriod(materialStockJSON.getLong("ExpPeriod") != null ? materialStockJSON.getLong("ExpPeriod") : 0);
        //在架寿命期
        materialStock.setOnlineLife(materialStockJSON.getLong("OnlineLife") != null ? materialStockJSON.getLong("OnlineLife") : 0);
        //参考成本
        materialStock.setRefCost(value(materialStockJSON.getString("RefCost")));
        //币别
        materialStock.setCurrencyIdName(value(getListValue("CurrencyId", "Name", materialStockJSON)));
        //库存管理
        materialStock.setIsSNManage(value(materialStockJSON.getString("IsSNManage")));
        //启用最小库存
        materialStock.setIsEnableMinStock(value(materialStockJSON.getString("IsEnableMinStock")));
        //启用安全库存
        materialStock.setIsEnableSafeStock(value(materialStockJSON.getString("IsEnableSafeStock")));
        //序列号编码规则
        materialStock.setSNCodeRule(value(materialStockJSON.getString("SNCodeRule")));
        //启用再订货点
        materialStock.setIsEnableReOrder(value(materialStockJSON.getString("IsEnableReOrder")));
        //启用最大库存
        materialStock.setIsEnableMaxStock(value(materialStockJSON.getString("IsEnableMaxStock")));
        //序列号单位
        materialStock.setSNUnit(value(getListValue("SNUnit", "Name", materialStockJSON)));
        //最小库存
        materialStock.setMinStock(value(materialStockJSON.getDouble("MinStock"), 0.0));
        //库存安全库存
        materialStock.setSafeStock(value(materialStockJSON.getDouble("SafeStock"), 0.0));
        //再订货点
        materialStock.setReOrderGood(value(materialStockJSON.getDouble("ReOrderGood"), 0.0));
        //经济订货批量
        materialStock.setEconReOrderQty(value(materialStockJSON.getDouble("EconReOrderQty"), 0.0));
        //最大库存
        materialStock.setMaxStock(value(materialStockJSON.getDouble("MaxStock"), 0.0));
        //生产追溯
        materialStock.setIsSNPRDTracy(value(materialStockJSON.getString("IsSNPRDTracy")));
        //单箱标准数量
        materialStock.setBoxStandardQty(value(materialStockJSON.getDouble("BoxStandardQty"), 0.0));
    }

    private String getSpNumber(String fieldOut, String fieldIn, String fieldInIn, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        if (jsonObject.get(fieldOut) != null) {
            JSONObject obj = jsonObject.getJSONObject(fieldOut);
            JSONObject inObj = obj != null ? obj.getJSONObject(fieldIn) : null;
            return inObj != null ? inObj.getString(fieldInIn) : null;
        }
        return null;
    }

    @Override
    public BDMaterial from(Map<String, Object> data) {
        return null;
    }
}