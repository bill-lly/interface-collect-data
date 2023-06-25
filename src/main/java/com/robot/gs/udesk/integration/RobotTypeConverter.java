package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RobotTypeConverter extends DataConverter<RobotType> {

  @Override
  public RobotType from(Map<String, Object> data) {
    RobotType type = new RobotType();
    type.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(type, field);
    }
    return type;
  }

  private void map(RobotType type, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        type.setName(value(field.get("fieldValue")));
        break;
      case "beizhu":
        type.setNote(value(field.get("fieldValue")));
        break;
      case "chanpinbianma":
        type.setProductSn(value(field.get("fieldValue")));
        break;
      case "chanpinxian":
        type.setRobotFamilyId(Long.parseLong(value(field.get("fieldValue"))));
        type.setRobotFamilyCode(value(field.get("foreignDataName")));
        break;
      case "zhengjibanbenhao":
        type.setMachineVersion(value(field.get("fieldValue")));
        break;

      case "owner":
      case "createUser":
      case "updateUser":
        break;

      case "createTime":
        type.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;
      case "updateTime":
        type.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
            dateTimeFormatter));
        break;

      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        type.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
