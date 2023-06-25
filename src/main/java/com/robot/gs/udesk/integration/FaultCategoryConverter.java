package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class FaultCategoryConverter extends DataConverter<FaultCategory>  {

  @Override
  public FaultCategory from(Map<String, Object> data) {
    FaultCategory faultCategory = new FaultCategory();
    faultCategory.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(faultCategory, field);
    }
    return faultCategory;
  }

  private void map(FaultCategory faultCategory, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        faultCategory.setName(value(field.get("fieldValue")));
        break;
      case "zhuangtai":
        faultCategory.setStatus(value(field.get("fieldValue")));
        break;
      case "yijifenlei":
        faultCategory.setTopCategory(value(field.get("fieldValue")));
        break;
      case "erjifenlei":
        faultCategory.setSecondCategory(value(field.get("fieldValue")));
        break;
      case "sanjifenlei":
        faultCategory.setThirdCategory(value(field.get("fieldValue")));
        break;
      case "guzhangbianhao":
        faultCategory.setFaultNo(value(field.get("fieldValue")));
        break;
      case "createTime":
        faultCategory.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        break;
      case "updateTime":
        faultCategory.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        break;
      case "createUser":
        faultCategory.setCreateUser(value(field.get("fieldValue")));
        break;
      case "updateUser":
        faultCategory.setUpdateUser(value(field.get("fieldValue")));
        break;
      case "owner":
        faultCategory.setOwner(value(field.get("fieldValue")));
        break;
      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        faultCategory.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
