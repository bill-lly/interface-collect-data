package com.robot.gs.udesk.integration;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TechTransProjectManagement {
    private Long id;
    //技改编号
    private String name;
    //技改项名称
    private String jigaixiangmingchen;
    //触发日期
    private LocalDate chufariqi;
    //技改数量
    private Long jigaishuliang;
    //完成数量
    private Long wanchengshuliang;
    //不能技改数量
    private Long bunenjigaishuliang;
    //进行中数量
    private Long jinxingzhong;
    //完成率
    private String wanchenglv;
    //是否换料
    private String shifuhuanliao;
    //到料时间
    private LocalDate daoliao;
    //到料套数
    private Long daoliaotaoshu;
    //完成时间
    private LocalDate wanchengshijian;
    //车号范围
    private String chehaofanwei;
    //备注
    private String beizhu;
    //技改状态
    private String jigaizhuangtai;
    //产品线
    private String chanpinxian_fieldValue;
    //产品线
    private String chanpinxian_foreignDataName;
    //技改等级
    private String jigaidengji;
    //技改类别
    private String jigaileibie;
    //技改项解决问题描述
    private String jigaixiangjiejuewentimiaoshu;
    //创建时间
    private LocalDateTime createTime;
    //创建人
    private String createUser;
    //更新时间
    private LocalDateTime updateTime;
    //修改人
    private String updateUser;
}
