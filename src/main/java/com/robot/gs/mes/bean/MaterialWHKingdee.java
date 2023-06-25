package com.robot.gs.mes.bean;

import lombok.Data;

@Data
public class MaterialWHKingdee {
    //车号
    private String model_number;
    //SN
    private String sn;
    //产品编号
    private String product_number;
    //生产订单号
    private String kingdee_mo_number;
    //入库时间
    private String datemodified;
    //入库编号
    private String kingdee_stockin_number;
    //提交时间
    private String datecreated;
    //车间
    private String workshop_number;
    //createdbyname
    private String createdbyname;
}
