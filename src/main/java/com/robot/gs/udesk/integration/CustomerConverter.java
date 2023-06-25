package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerConverter extends DataConverter<Customer> {

  @Override
  public Customer from(Map<String, Object> data) {
    Customer customer = new Customer();
    customer.setId(Long.parseLong(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(customer, field);
    }
    return customer;
  }

  private void map(Customer customer, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        customer.setName(value(field.get("fieldValue")));
        break;
      case "sysOrganizationNo":
        customer.setSn(value(field.get("fieldValue")));
        break;
      case "sysGrade":
        customer.setGrade(value(field.get("fieldValue")));
        break;

      case "province":
        customer.setProvince(value(field.get("fieldValue")));
        break;
      case "city":
        customer.setCity(value(field.get("fieldValue")));
        break;
      case "county":
        customer.setCounty(value(field.get("fieldValue")));
        break;

      case "sysDetailedAddress":
        customer.setAddress(value(field.get("fieldValue")));
        break;

      case "coordinate":
        customer.setCoordinate(value(field.get("fieldValue")));
        break;

      case "beizhu":
        customer.setNote(value(field.get("fieldValue")));
        break;

      case "kehuleixing":
        customer.setCustomerType(value(field.get("fieldValue")));
        break;

      case "shifuyouxiaokehu":
        customer.setIsEfficientCustomer(value(field.get("fieldValue")));
        break;
      case "daquXianSuo":
        customer.setBusinessArea(value(field.get("fieldValue")));
        break;
      case "kehuxingzhi":
        customer.setCustomerNature(value(field.get("fieldValue")));
        break;
      case "guojia":
        customer.setCountry(value(field.get("fieldValue")));
        break;
      case "kehuxingye":
        customer.setCustomerIndustry(value(field.get("fieldValue")));
        break;

      case "kehushenfen":
        customer.setCustomerIdentity(value(field.get("fieldValue")));
        break;

      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        customer.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
