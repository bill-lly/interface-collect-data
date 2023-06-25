package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
public class UserInfoConverter extends DataConverter<UserInfo>{

  @Override
  public UserInfo from(Map<String, Object> data) {
    UserInfo userInfo = new UserInfo();
    userInfo.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(userInfo, field);
    }
    return userInfo;
  }

  private void map(UserInfo userInfo, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "staffnumber":
        userInfo.setEmployeeNo(value(field.get("fieldValue")));
        break;
      case "sysusername":
        userInfo.setName(value(field.get("fieldValue")));
        break;
      case "bumen":
        userInfo.setDepartment(value(field.get("fieldValue")));
        break;
      case "userJobNumber":
        userInfo.setEmployeeId(value(field.get("fieldValue")));
        break;
      case "userMobile":
        userInfo.setPhone(value(field.get("fieldValue")));
        break;
      case "userEmail":
        userInfo.setEmail(value(field.get("fieldValue")));
        break;
      case "suoshuquyu":
        userInfo.setRegion(value(field.get("fieldValue")));
        break;
      case "suoshufuwuzhan":
        userInfo.setServiceStation(value(field.get("fieldValue")));
        break;
      case "suoshucangku":
        userInfo.setWarehouse(value(field.get("fieldValue")));
        userInfo.setWareHouseName(value(field.get("foreignDataName")));
        break;
      case "userJobStatus":
        userInfo.setJobStatus(value(field.get("fieldValue")));
        break;
      case "userStatus":
        userInfo.setUserStatus(value(field.get("fieldValue")));
        break;
      case "userRoleIds":
        userInfo.setUserRoleIds(value(field.get("fieldValue")));
        break;
      case "ruzhishijian":
        userInfo.setEntryDate(LocalDate.parse(field.get("fieldValue").toString(), dateTimeFormatter.ofPattern("yyyy-MM-dd")));
        break;
      case "ruzhishichang":
        userInfo.setEntryDuration(Integer.parseInt(value(field.get("fieldValue"))));
        break;
      case "createTime":
        userInfo.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        break;
      case "updateTime":
        userInfo.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        break;
      case "createUser":
        userInfo.setCreateUser(value(field.get("fieldValue")));
        break;
      case "updateUser":
        userInfo.setUpdateUser(value(field.get("fieldValue")));
        break;
      case "zaizhizhuangtai":
        userInfo.setWorkerStatus(value(field.get("fieldValue")));
        break;
      case "renyuanshuxing":
        userInfo.setWorkerProperty(value(field.get("fieldValue")));
        break;
      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        userInfo.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }

}
