package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargoMangement {
    private Long id;
    //配件编码
    private String goodsPn;
    //配件名称
    private String goodsName;
    //配件类别
    private String peijianleibie;
    //产品线
    private String chanpinxian;
    //整机版本
    private String zhengjibanben;
    //核销类型
    private String hexiaoleixing;
    //是否可申请
    private String shifukeshenqing;
    //渠道单价
    private Double realPrice;
    //用户单价
    private Double sellPrice;
    //计价单位
    private String morenjijiadanwei;
    //BOM名称
    private String BOMmingchen;
    //备注
    private String beizhu;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //修改人
    private String updateUser;
    //实物照片
    private String shiwuzhaopian;
    //配件编码（导入用唯一字段）
    private String peijianbianmadaoruziduan;
    //关键字
    private String guanjianzi;
    //记录编号
    private String goodsNumber;
    //是否自动订购
    private String shifuzidongdinggou;
    //技改物料
    private String jigaiwuliao;
    //售后机型
    private String shouhoujixing;
    //配件类型
    private String peijianleixing;
    //可用数量
    private Long availableQuantity;
    //货品数量
    private Long quantityOfGoods;
    //锁定数量
    private Long lockedQuantity;
    //所属货类
    private String suoshuhuolei;
    //描述
    private String describe;
    //停用
    private String shortName;
    //币种
    private String bizhong;
    //所属大区
    private String suoshudaqu;
    //产品SN序列号
    private String chanpinSNxuliehao;
    //开启批次
    private String lotSet;
    //货品状态
    private String cargoState;
    //旧配件编码
    private String jiupeijianbianma;
    //供应状态
    private String gongyingzhuangtai;
    //跑数据
    private String paoshuju;
    //规格
    private String guige;
    //更换周期
    private String genghuanzhouqi;
    //用量
    private String yongliang;
    //作业模式
    private String zuoyemoshi;
    //清洁配置
    private String qingjiepeizhi;
    //技改项
    private String jigaixiang;
    //成本单价
    private Double chengbendanjia;
    //Parts Name
    private String PartsName;
    //赋值字段
    private String fuzhiziduan;
    //创建人
    private String createUser;
    //所有人
    private String owner;
}
