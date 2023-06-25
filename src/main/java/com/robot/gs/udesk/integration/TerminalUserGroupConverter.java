package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalUserGroupConverter extends DataConverter<TerminalUserGroup> {

  @Override
  public TerminalUserGroup from(Map<String, Object> data) {
    TerminalUserGroup group = new TerminalUserGroup();
    group.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(group, field);
    }
    return group;
  }

  private void map(TerminalUserGroup group, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        group.setName(value(field.get("fieldValue")));
        break;
      case "kehuzubianhao":
        group.setSn(value(field.get("fieldValue")));
        break;
      case "kehuyouxianji":
        group.setCustomerGrade(value(field.get("fieldValue")));
        break;
      case "chengjiaofangshi":
        group.setBargainMode(value(field.get("fieldValue")));
        break;
      case "zhongduanyetai":
        group.setTerminalIndustry(value(field.get("fieldValue")));
        break;
      case "dakehujingli":
        group.setKeyAccountManager(value(field.get("foreignDataName")));
        break;
      case "xiaoshoufuzeren":
        group.setHeadOfSales(value(field.get("foreignDataName")));
        break;
      case "guojia":
        group.setCountry(value(field.get("fieldValue")));
        break;
      case "jituangongsiquanchen":
        group.setGroupCompanyName(value(field.get("foreignDataName")));
        break;
      case "kehuleibie":
        group.setCustomerCategory(value(field.get("fieldValue")));
        break;
      case "xingyeshuxing":
        group.setIndustryAttribute(value(field.get("fieldValue")));
        break;
      case "createTime":
        group.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "updateTime":
        group.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(), dateTimeFormatter));
        break;
      case "createUser":
        group.setCreateUserId(Long.parseLong(value(field.get("fieldValue"))));
        group.setCreateUserEmail(value(field.get("userEmail")));
        break;
      case "updateUser":
      case "owner":
        break;

      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        group.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
