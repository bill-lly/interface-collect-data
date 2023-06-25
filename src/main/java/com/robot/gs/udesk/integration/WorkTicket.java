package com.robot.gs.udesk.integration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class WorkTicket {
  private long id;

  //工单描述
  private String subject;
  //故障描述
  private String faultDescription;
  //故障分类
  private String faultType;
  //工单编号
  private String sn;
  //工单类别
  private String ticketType;

  private long robotArchiveId;
  //设备S/N号
  private String robotProductId;
  //所属服务站
  private String operationalRegionName;
  private long operationalRegionId;
  //结费类型
  private String feeType;
  //派单时间
  private LocalDateTime dispatchTime;
  //派工备注
  private String dispatchNote;
  //合同客户
  private String contractedCustomerName;
  private long contractedCustomerId;

  //省
  private String province;
  //市
  private String city;
  //区/县
  private String county;
  //工单状态
  private String status;
  //保内保外
  private String isUnderWarranty;
  //负责FAE
  private long faeId;
  private String faeEmail;
  //售后机型
  private String robotModelTypeVersion;
  private long robotModelTypeVersionId;
  //服务方式
  private String serviceMode;
  //服务项目费用
  private double serviceItemCharge;
  //服务费用小计
  private double serviceCharge;
  //服务工时费用
  private double manHourCharge;
  //配件费用小计
  private double attachmentCharge;
  //其他费用
  private double otherCharge;
  //总计费用
  private double totalCharge;
  //所属大区
  private String businessArea;
  //维修类别
  private String maintenanceCategory;
  //维修类别二级
  private String maintenanceSecondCategory;
  //预约时间
  private LocalDateTime appointmentTime;
  //到达时间
  private LocalDateTime arrivalTime;
  //完工时间
  private LocalDateTime finishTime;
  //签到
  private long punchInId;
  private String punchInLocation;
  //签退
  private long punchOutId;
  private String punchOutLocation;
  //服务站类型
  private String businessType;
  //维修描述
  private String maintenanceDescription;
  //完工备注
  private String workNote;
  //审批状态
  private String approvalStatus;
  //创建人
  private long createUserId;
  private String createUserEmail;
  //服务项目费用（统计）
  private double serviceChargeStats;
  //服务工时费用（统计）
  private double manHourChargeStats;
  //其他费用（统计）
  private double otherChargeStats;
  //客户联系人
  private long contactId;
  private String contactName;
  //故障现象
  private String faultSymptom;
  //终端客户
  private long terminalUserId;
  private String terminalUserName;

  //适用工时城市
  private String manHourCity;
  //适用工时国家
  private String manHourCountry;
  //交付总耗时
  private Integer deliverySpent;
  //交货日期
  private LocalDateTime deliveryDate;
  //进度更新时间
  private LocalDateTime processUpdateTime;
  //技改编号
  private String modificationCode;
  //处理进度
  private String processingProgress;
  //开箱损
  private String whetherUnbox;
  //技改完工分类
  private String modificationClass;
  //客户组
  private String customerGroup;
  //客户等级
  private String customerClass;
  //数据来源
  private String dataSource;

  //产品线
  private String productLine;
  //是否删除
  private long isDelete;
  //故障确认
  private String faultConfirm;

  LocalDateTime createTime;
  LocalDateTime updateTime;
  private Map<String, FieldRecord> extraFields = new HashMap<>();
  //软件版本信息
  private String softwareVersionTicket;
}
