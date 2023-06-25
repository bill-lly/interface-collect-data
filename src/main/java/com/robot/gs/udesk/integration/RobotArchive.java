package com.robot.gs.udesk.integration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class RobotArchive {
  private long id;
  private String productId;
  //合同客户
  private String contractedCustomerName;
  private long contractedCustomerId;
  //售后机型
  private String robotType;
  private long robotTypeId;

  //出厂日期
  private LocalDate productionDate;
  //保修截止日期
  private LocalDate warrantyExpirationDate;
  //保修状态
  private String warrantyStatus;
  //安装/交付日期
  private LocalDate deliveryDate;
  //所属大区
  private String businessArea;
  //国家
  private String country;
  //省
  private String province;
  //市
  private String city;
  //区/县
  private String county;
  //专属FAE
  private String faeEmail;
  private String faeName = "";
  //专属FAE电话
  private String faePhone;
  //地址
  private String address;
  //场景
  private String scene;
  //场景二级分类-部署环境
  private String deploymentEnvironment;
  //所属服务站
  private String operationalRegionName;
  private long operationalRegionId;
  //有充电桩/工作站
  private String hasChargingStation;
  //充电桩/工作站S/N
  private String chargingStation;
  //是否融资租赁
  private String isFinancialLeasing;
  //融资的租赁方式
  private String financialLeasingMode;
  private String orderType;

  //适用工时城市
  private String manHourCity;
  //适用工时国家
  private String manHourCountry;
  //机器名称
  private String robotName;
  //客户等级
  private String customerGrade;
  private String lifeCycle;
  private String robotFamilyCode;

  //终端客户
  private long terminalUserId;
  private String  terminalUserName;
  //耗材供应
  private String consumableSupply;
  //工作模式 （洗地、尘推等）
  private String cleaningMode;
  //整机版本号
  private String machineVersion;

  //外设
  private String peripherals;
  //附件配置
  private String attachment;
  //室内/外
  private String indoorOrOutdoor;

  private String status;
  //洗地或尘推
  private String cleaningOrDustMop;

  //是否激活
  private String doesActivate;
  //安装位置
  private String installationPosition;
  //激光型号
  private String laserTypeCode;
  //下位机版本
  private String mcuVersion;
  //APP版本信息
  private String appVersion;
  //路由驱动版本
  private String routerVersion;
  //是否加装神经棒
  private String doesAssembleNeuralStick;
  //系统版本
  private String systemVersion;
  //软件版本信息
  private String softwareVersion;
  //下位机型号
  private String mcuType;
  //摄像头ID
  private String cameraId;
  //摄像头型号
  private String cameraType;
  //激光序列号
  private String laserNumber;
  //备注
  private String note;
  //目标面积"
  private double targetArea;

  //外设清单
  private String peripheralList;
  //自营保洁
  private String directClean;
  // 不联网原因
  private String noNetworkReason;
  // 机器人车号
  private String productCarNo;
  private Map<String, FieldRecord> extraFields = new HashMap();
  private LocalDateTime createTime;
  private LocalDateTime updateTime;
}
