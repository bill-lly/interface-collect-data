package com.robot.gs.ticket.http.udesk.enums.archive;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ArchiveFieldDataEnum {
  BELONGS_REGION("belongsRegion", "所属运维大区", "", "是"),
  SN("name", "设备S/N号", "", "否"),
  ALIAS("jiqimingchen", "机器人名称", "", "是"),
  ROBOT_FAMILY_CODE("chanpinxian", "产品线", "", "是"),
  APP_VERSION("APPbanbenxinxi", "APP版本号", "", "是"),
  MACHINE_VERSION("zhengjibanbenhao", "整机版本号", "", "是"),
  SOFTWARE_VERSION("ruanjianbanbenxinxi", "软件版本信息", "", "是"),
  HARDWARE_VERSION_1("xiaweijibanben", "下位机版本", "", "是"),
  ROUTER_VERSION("luyouqudongbanben", "路由驱动版本", "", "是"),
  SYSTEM_VERSION("xitongbanben", "系统版本", "", "是"),
  SCENE("useScene", "使用场景", "", "是"),
  PERIPERIAL("waishe", "外设", "", "是"),
  ACCESSORIES_CONFIG("fujianpeizhi", "附件配置", "", "是"),
  EXPAND_COMMENT("yichangshuoming", "异常说明", "", "是"),
  LASER_NUMBER("jiguangxuliehao", "激光序列号", "", "是"),
  LASER_TYPE("jiguangxinghao", "激光型号", "", "是"),
  LIFE_CYCLE("yongtu", "用途", "", "是"),
  IS_ACTIVE("shifujihuo", "是否激活", "", "是"),
  STATION_NAME("suoshufuwuzhan","所属服务站","","是"),
  DATA_SOURCE("shujulaiyuan", "数据来源", "云平台", "否");

  ArchiveFieldDataEnum(String key, String description, String defaultValue, String isNeedUpdate) {
    this.key = key;
    this.description = description;
    this.defaultValue = defaultValue;
    this.isNeedUpdate = isNeedUpdate;

  }

  private String key;
  private String description;
  private String defaultValue;

  public String getIsNeedUpdate() {
    return isNeedUpdate;
  }

  private String isNeedUpdate;

  public String getKey() {
    return key;
  }

  public String getDescription() {
    return description;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public static Set<String> getAllKey() {
    return Arrays.stream(values()).map(e -> e.getKey())
        .collect(Collectors.toSet());
  }

  public static Set<String> getAllNeedUpdateKey() {
    return Arrays.stream(values()).filter(e -> "是".equals(e.getIsNeedUpdate())).map(e -> e.getKey())
        .collect(Collectors.toSet());
  }
}
