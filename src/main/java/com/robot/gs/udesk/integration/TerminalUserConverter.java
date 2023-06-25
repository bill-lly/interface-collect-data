package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalUserConverter extends DataConverter<TerminalUser> {

  @Override
  public TerminalUser from(Map<String, Object> data) {
    TerminalUser user = new TerminalUser();
    user.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(user, field);
    }
    return user;
  }

  private void map(TerminalUser user, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        user.setName(value(field.get("fieldValue")));
        break;
      case "kehudizhi":
        user.setAddress(value(field.get("fieldValue")));
        break;
      case "kehudengji":
        user.setCustomerGrade(value(field.get("fieldValue")));
        break;
      case "suoshudaqu":
        user.setBusinessArea(value(field.get("fieldValue")));
        break;
      case "kehubianhao":
        user.setCustomerSn(value(field.get("fieldValue")));
        break;
      case "zhongduankehu":
        user.setFullName(value(field.get("fieldValue")));
        break;
      case "kehuzu":
        user.setGroupId(Long.parseLong(field.get("fieldValue").toString()));
        user.setGroupName(value(field.get("foreignDataName")));
        break;
      case "zhongduankehubianhao":
        user.setSn(value(field.get("fieldValue")));
        break;
      case "province":
        user.setProvince(value(field.get("fieldValue")));
        break;
      case "city":
        user.setCity(value(field.get("fieldValue")));
        break;
      case "county":
        user.setCounty(value(field.get("fieldValue")));
        break;

      case "owner":
      case "createUser":
      case "updateUser":
        break;

      case "createTime":
        user.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "updateTime":
        user.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "shoujianren":
        user.setReceiverName(value(field.get("fieldValue")));
        break;
      case "shoujianrendianhua":
        List<Map<String, Object>> phoneList = (List<Map<String, Object>>)field.get("tagValueList");
        Map<String, String> phoneMap = new HashMap<>();
        for (Map<String, Object> phone : phoneList) {
          String key = (String)phone.get("tagName");
          String value = (String)phone.get("tagValue");
          phoneMap.put(key, value);
        }
        String phoneJsonStr = gson.toJson(phoneMap);
        user.setReceiverPhone(value(phoneJsonStr));
        break;
      case "zuobiao":
        user.setCoordinate(value(field.get("fieldValue")));
        break;
      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        user.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
