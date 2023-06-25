package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * https://servicego.udesk.cn/site/admin/custom-object/4973/fields
 */
@Slf4j
public class RobotArchiveConverter extends DataConverter<RobotArchive> {

  @Override
  public RobotArchive from(Map<String, Object> data) {
    RobotArchive archive = new RobotArchive();
    archive.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(archive, field);
    }
    return archive;
  }

  private void map(RobotArchive archive, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        archive.setProductId(value(field.get("fieldValue")));
        break;
      case "belongsCustomer":
        archive.setContractedCustomerId(Long.parseLong(field.get("fieldValue").toString()));
        archive.setContractedCustomerName(field.get("foreignDataName").toString());
        break;
      case "jixing":
        archive.setRobotTypeId(Long.parseLong(field.get("fieldValue").toString()));
        archive.setRobotType(value(field.get("foreignDataName")));
        break;
      case "releaseDate":
        archive.setProductionDate(LocalDate.parse(field.get("fieldValue").toString()));
        break;
      case "equipmentDate":
        archive.setWarrantyExpirationDate(LocalDate.parse(field.get("fieldValue").toString()));
        break;
      case "deliveryDate":
        archive.setDeliveryDate(LocalDate.parse(field.get("fieldValue").toString()));
        break;
      case "belongsRegion":
        archive.setBusinessArea(value(field.get("fieldValue")));
        break;
      case "country":
          archive.setCountry(field.get("fieldValue")!=null?field.get("fieldValue").toString():"");
        break;
      case "province":
        archive.setProvince(value(field.get("fieldValue")));
        break;
      case "city":
        archive.setCity(value(field.get("fieldValue")));
        break;
      case "county":
        archive.setCounty(value(field.get("fieldValue")));
        break;
      case "zhuanshuFAE":
        archive.setFaeEmail(value(field.get("userEmail")));
        //archive.setFaeName(field.get("foreignDataName").toString());
        break;
      case "FAEdianhua":
        List<Map<String, Object>> faePhoneList = (List<Map<String, Object>>)field.get("tagValueList");
        Map<String, String> phoneMap = new HashMap<>();
        for (Map<String, Object> faePhone : faePhoneList) {
          String key = (String)faePhone.get("tagName");
          String value = (String)faePhone.get("tagValue");
          phoneMap.put(key, value);
        }
        String feaPhoneJsonStr = gson.toJson(phoneMap);
        archive.setFaePhone(value(feaPhoneJsonStr));
        break;
      case "address":
        archive.setAddress(value(field.get("fieldValue")));
        break;
      case "useScene":
        archive.setScene(value(field.get("fieldValue")));
        break;
      case "suoshufuwuzhan":
        archive.setOperationalRegionId(Long.parseLong(field.get("fieldValue").toString()));
        archive.setOperationalRegionName(value(field.get("foreignDataName")));
        break;
      case "yougongzuozhan":
        archive.setHasChargingStation(value(field.get("fieldValue")));
        break;
      case "chongdianzhuanggongzuozhanSNhao":
        archive.setChargingStation(value(field.get("fieldValue")));
        break;

      case "shifurongzizulin":
        archive.setIsFinancialLeasing(value(field.get("fieldValue")));
        break;
      case "rongzidezulinfangshi":
        archive.setFinancialLeasingMode(value(field.get("fieldValue")));
        break;

      case "dingdanleixing":
        archive.setOrderType(value(field.get("fieldValue")));
        break;

      case "shiyonggongshichengshi":
        archive.setManHourCity(value(field.get("fieldValue")));
        break;
      case "jiqimingchen":
        archive.setRobotName(value(field.get("fieldValue")));
        break;
      case "bulianwangyuanyin":
        archive.setNoNetworkReason(value(field.get("fieldValue")));
        break;
      case "kehudengji":
        archive.setCustomerGrade(value(field.get("fieldValue")));
        break;
      case "yongtu":
        archive.setLifeCycle(value(field.get("fieldValue")));
        break;

      case "chanpinxian":
        archive.setRobotFamilyCode(value(field.get("foreignDataName")));
        break;
      case "zhongduankehu1":
        archive.setTerminalUserId(Long.parseLong(field.get("fieldValue").toString()));
        archive.setTerminalUserName(field.get("foreignDataName").toString());
        break;

      case "haocaigongying":
        archive.setConsumableSupply(value(field.get("fieldValue")));
        break;
      case "gongzuomoshixidichentuideng":
        archive.setCleaningMode(value(field.get("fieldValue")));
        break;
      case "zhengjibanbenhao":
        archive.setMachineVersion(value(field.get("fieldValue")));
        break;
      case "waishe":
        archive.setPeripherals(value(field.get("fieldValue")));
        break;
      case "fujianpeizhi":
        archive.setAttachment(value(field.get("fieldValue")));
        break;

      case "shinawai":
        archive.setIndoorOrOutdoor(value(field.get("fieldValue")));
        break;
      case "status":
        archive.setStatus(value(field.get("fieldValue")));
        break;
      case "xidihuochentui":
        archive.setCleaningOrDustMop(value(field.get("fieldValue")));
        break;
      case "baoxiuzhuangtai":
        archive.setWarrantyStatus(field.get("fieldValue").toString());
        break;
      case "shifujihuo":
        archive.setDoesActivate(value(field.get("fieldValue")));
        break;
      case "installationSite":
        archive.setInstallationPosition(value(field.get("fieldValue")));
        break;

      case "createTime":
        archive.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        break;
      case "updateTime":
        archive.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "xitongbanben":
        archive.setSystemVersion(value(field.get("fieldValue")));
        break;
      case "ruanjianbanbenxinxi":
        archive.setSoftwareVersion(value(field.get("fieldValue")));
        break;
      case "APPbanbenxinxi":
        archive.setAppVersion(value(field.get("fieldValue")));
        break;
      case "xiaweijibanben":
        archive.setMcuVersion(value(field.get("fieldValue")));
        break;
      case "luyouqudongbanben":
        archive.setRouterVersion(value(field.get("fieldValue")));
        break;
      case "jiguangxuliehao":
        archive.setLaserNumber(value(field.get("fieldValue")));
        break;
      case "jiguangxinghao":
        archive.setLaserTypeCode(value(field.get("fieldValue")));
        break;
      case "shexiangtouxinghao":
        archive.setCameraType(value(field.get("fieldValue")));
        break;
      case "shexiangtouID":
        archive.setCameraId(value(field.get("fieldValue")));
        break;
      case "shifujiazhuangshenjingbang":
        archive.setDoesAssembleNeuralStick(value(field.get("fieldValue")));
        break;

      case "remark":
        archive.setNote(value(field.get("fieldValue")));
        break;
      case "xiaweijixinghao":
        archive.setMcuType(value(field.get("fieldValue")));
        break;
      case "renwumubiao":
        archive.setTargetArea(Double.parseDouble(value(field.get("fieldValue"), "0")));
        break;
      case "ziyingbaojie":
        archive.setDirectClean(value(field.get("fieldValue")));
        break;
      case "waisheqingdan":
        archive.setPeripheralList(value(field.get("fieldValue")));
        break;
      case "jiqirenchehao":
        archive.setProductCarNo(value(field.get("fieldValue")));
        break;
      case "bushuhuanjing":
        archive.setDeploymentEnvironment(value(field.get("fieldValue")));
        break;

      case "denglumima":
      case "owner":
      case "createUser":
      case "updateUser":
        break;

      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        archive.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }

}
