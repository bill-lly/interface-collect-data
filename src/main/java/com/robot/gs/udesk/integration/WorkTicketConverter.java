package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkTicketConverter extends DataConverter<WorkTicket> {

  @Override
  public WorkTicket from(Map<String, Object> data) {
    WorkTicket order = new WorkTicket();
    order.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(order, field);
    }
    return order;
  }

  private void map(WorkTicket order, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "subject":
        order.setSubject(value(field.get("fieldValue")));
        break;
      case "description":
        order.setFaultDescription(value(field.get("richText"), "{}"));
        break;
      case "guzhangfenlei":
        order.setFaultType(value(field.get("fieldValue")));
        break;
      case "gongdanbianhao":
        order.setSn(value(field.get("fieldValue")));
        break;
      case "gongdanleibie":
        order.setTicketType(value(field.get("fieldValue")));
        break;
      case "shebeiSNhao":
        order.setRobotArchiveId(Long.parseLong(value(field.get("fieldValue"), "0")));
        order.setRobotProductId(value(field.get("foreignDataName")));
        break;
      case "suoshufuwuzhan":
        order.setOperationalRegionId(Long.parseLong(field.get("fieldValue").toString()));
        order.setOperationalRegionName(value(field.get("foreignDataName")));
        break;
      case "shoufeileixing":
        order.setFeeType(value(field.get("fieldValue")));
        break;
      case "paidanshijian":
        order.setDispatchTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "belongsCustomer":
        order.setContractedCustomerId(Long.parseLong(field.get("fieldValue").toString()));
        order.setContractedCustomerName(field.get("foreignDataName").toString());
        break;

      case "province":
        order.setProvince(value(field.get("fieldValue")));
        break;
      case "city":
        order.setCity(value(field.get("fieldValue")));
        break;
      case "county":
        order.setCounty(value(field.get("fieldValue")));
        break;
      case "gongdanzhuangtai":
        order.setStatus(value(field.get("fieldValue")));
        break;
      case "baonabaowai":
        order.setIsUnderWarranty(value(field.get("fieldValue")));
        break;
      case "ownerEngine":
        order.setFaeId(Long.parseLong(value(field.get("fieldValue"))));
        order.setFaeEmail(value(field.get("userEmail")));
        break;
      case "shouhoujixing":
        order.setRobotModelTypeVersion(value(field.get("foreignDataName")));
        order.setRobotModelTypeVersionId(Long.parseLong(value(field.get("fieldValue"), "0")));
        break;
      case "fuwuleibie":
        order.setServiceMode(value(field.get("fieldValue")));
        break;
      case "xiangmu":
        order.setServiceItemCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "fuwufeiyongxiaoji":
        order.setServiceCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "gongshifei":
        order.setManHourCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "peijianfei":
        order.setAttachmentCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "qitafeiyong":
        order.setOtherCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "zongjifeiyong":
        order.setTotalCharge(Double.parseDouble(value(field.get("fieldValue"),"0")));
        break;
      case "suoshudaqu":
        order.setBusinessArea(value(field.get("fieldValue")));
        break;
      case "way":
        order.setMaintenanceCategory(value(field.get("fieldValue")));
        break;
      case "weixiuleibieerji":
        order.setMaintenanceSecondCategory(value(field.get("fieldValue")));
        break;
      case "yuyueshijian":
        order.setAppointmentTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "daodashijian":
        order.setArrivalTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "wangongshijian":
        order.setFinishTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "qiandao":
        order.setPunchInId(Long.parseLong(value(field.get("fieldValue"), "0")));
        order.setPunchInLocation(value(field.get("foreignDataName")));
        break;
      case "qiantui":
        order.setPunchOutId(Long.parseLong(value(field.get("fieldValue"), "0")));
        order.setPunchOutLocation(value(field.get("foreignDataName")));
        break;
      case "fuwuzhanleixing":
        order.setBusinessType(value(field.get("fieldValue")));
        break;
      case "weixiumiaoshu":
        order.setMaintenanceDescription(value(field.get("fieldValue")));
        break;
      case "wangongbeizhu":
        order.setWorkNote(value(field.get("fieldValue")));
        break;
      case "shenpizhuangtai":
        order.setApprovalStatus(value(field.get("fieldValue")));
        break;
      case "createUser":
        order.setCreateUserId(Long.parseLong(value(field.get("fieldValue"))));
        order.setCreateUserEmail(value(field.get("userEmail")));
        break;
      case "fuwuxiangmufeiyong":
        order.setServiceChargeStats(Double.parseDouble(value(field.get("fieldValue"), "0")));
        break;
      case "gongshifeiyongtongji":
        order.setManHourChargeStats(Double.parseDouble(value(field.get("fieldValue"), "0")));
        break;
      case "waichufeiyongtongji":
        order.setOtherChargeStats(Double.parseDouble(value(field.get("fieldValue"), "0")));
        break;
      case "contact":
        order.setContactId(Long.parseLong(value(field.get("fieldValue"), "0")));
        order.setContactName(value(field.get("foreignDataName")));
        break;
      case "paigongbeizhu":
        order.setDispatchNote(value(field.get("fieldValue")));
        break;
      case "guzhangxianxiang":
        order.setFaultSymptom(value(field.get("fieldValue")));
        break;
      case "zhongduankehu":
        order.setTerminalUserId(Long.parseLong(value(field.get("fieldValue"))));
        order.setTerminalUserName(value(field.get("foreignDataName")));
        break;
      case "shiyonggongshichengshi":
        order.setManHourCity(value(field.get("fieldValue")));
        break;
      case "shiyonggongshiguojia":
        order.setManHourCountry(value(field.get("fieldValue")));
        break;
      case "gongchengshidianhua":
        break;
      case "jiaofuzonghaoshi":
        order.setDeliverySpent(toInt(field.get("fieldValue")));
        break;
      case "daohuoriqi":
        order.setDeliveryDate(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "jindugengxinshijian":
        order.setProcessUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "jigaibianhao1":
        //order.setModificationCode(value(field.get("fieldValue")));
        order.setModificationCode(value(field.get("foreignDataName")));
        break;
      case "chulijindu":
        order.setProcessingProgress(value(field.get("fieldValue")));
        break;
      case "kaixiangsun":
        order.setWhetherUnbox(value(field.get("fieldValue")));
        break;
      case "jigaiwangongfenlei":
        order.setModificationClass(value(field.get("fieldValue")));
        break;
      case "kehuzu":
        //order.setCustomerGroup(value(field.get("fieldValue")));
        order.setCustomerGroup(value(field.get("foreignDataName")));
        break;
      case "kehudengji":
        order.setCustomerClass(value(field.get("fieldValue")));
        break;
      case "shujulaiyuan":
        order.setDataSource(value(field.get("fieldValue")));
        break;
      case "createTime":
        order.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "updateTime":
        order.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "chanpinxian":
        order.setProductLine(value(field.get("foreignDataName")));
        break;
      case "guzhanggaojingqueren":
        order.setFaultConfirm(value(field.get("fieldValue")));
        break;
      case "ruanjianbanbenxinxi":
        order.setSoftwareVersionTicket(value(field.get("fieldValue")));
        break;
      default:
        //目前udesk删除权限已回收，默认置0，主要处理历史存量已删除订单逻辑
        order.setIsDelete(0);
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        order.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
