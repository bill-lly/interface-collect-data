package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReplacementPartUse {
    private Long id;
    //使用单编号
    private String name;
    //领用数量
    private Long shenqingshuliang;
    //是否使用出库
    private String shifushiyongchuku;
    //所属工单编号
    private String suoshugongdanbianhao;
    //所属工单
    private String suoshugongdan_fieldValue;
    //所属工单
    private String suoshugongdan_foreignDataName;
    //用户单价
    private Double feiyong;
    //机型
    private String suoshuchanpin;
    //使用单状态
    private String shenpizhuangtai;
    //渠道单价
    private Double qudaodanjia;
    //配件总价
    private Double peijianzongjia;
    //所属大区
    private String suoshudaqu;
    //配件编码
    private String peijianbianma1;
    //配件名称
    private String shiyongpeijianhuopin_fieldValue;
    //配件名称
    private String shiyongpeijianhuopin_foreignDataName;
    //核销类型
    private String hexiaoleixing;
    //已使用数量
    private Long shiyongshuliang;
    //本次使用数量
    private Long bencishiyongshuliang;
    //累计使用数量
    private Long leijishiyongshuliang;
    //上次使用数量
    private Long shangcishiyongshuliang;
    //使用说明
    private String shiyongshuoming;
    //领用原因
    private String lingyongyuanyin;
    //退回仓库数量
    private Long tuihuicangkushuliang;
    //用户总价
    private Double yonghuzongjia;
    //剩余未使用数量
    private Long shengyuweishiyongshuliang;
    //服务站
    private String fuwuzhan_fieldValue;
    //服务站
    private String fuwuzhan_foreignDataName;
    //服务站站长
    private String fuwuzhanzhanchang;
    //使用类型
    private String shiyongleixing;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
    //创建人
    private String createUser_fieldValue;
    //已核销数量
    private Long yihexiaoshuliang;
    //所属仓库
    private String suoshucangk_fieldValue;
    private String suoshucangk_foreignDataName;
    //创建人email
    private String createUser_userEmail;
    //所有人
    private String owner_fieldValue;
    private String owner_ownerResult;
}
