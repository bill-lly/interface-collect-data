package com.robot.gs.mes.converter;

import com.alibaba.fastjson2.JSONObject;
import com.robot.gs.mes.bean.MaterialWHKingdee;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;


@Slf4j
public class MaterialWHKingdeeConverter extends MesDataConverter<MaterialWHKingdee> {
    public MaterialWHKingdee from(JSONObject data) {
        MaterialWHKingdee materialWHKingdee = new MaterialWHKingdee();
            map(materialWHKingdee, data);
        return materialWHKingdee;
    }

    private void map(MaterialWHKingdee materialWHKingdee, JSONObject data) {
        //生产订单号
        materialWHKingdee.setKingdee_mo_number(value(data.getString("kingdee_mo_number")));
        //入库时间
        materialWHKingdee.setDatemodified(value(data.getString("datemodified")));
        //入库编号
        materialWHKingdee.setKingdee_stockin_number(value(data.getString("kingdee_stockin_number")));
        //提交时间
        materialWHKingdee.setDatecreated(value(data.getString("datecreated")));
        //车间
        materialWHKingdee.setWorkshop_number(value(data.getString("workshop_number")));
        //createdbyname
        materialWHKingdee.setCreatedbyname(value(data.getString("createdbyname")));
        //车号
        materialWHKingdee.setModel_number(value(data.getString("model_number")));
        //SN
        materialWHKingdee.setSn(value(data.getString("sn")));
        //产品编号
        materialWHKingdee.setProduct_number(value(data.getString("product_number")));
    }

    @Override
    public MaterialWHKingdee from(Map<String, Object> data) {
        return null;
    }
}