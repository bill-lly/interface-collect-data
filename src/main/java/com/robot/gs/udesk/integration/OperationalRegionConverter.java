package com.robot.gs.udesk.integration;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OperationalRegionConverter extends DataConverter<OperationalRegion> {

  @Override
  public OperationalRegion from(Map<String, Object> data) {
    OperationalRegion region = new OperationalRegion();
    region.setId(Integer.parseInt(data.get("id").toString()));
    List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
    for (Map<String, Object> field : fieldList) {
      map(region, field);
    }
    return region;
  }


  private void map(OperationalRegion region, Map<String, Object> field) {
    switch (field.get("fieldApiName").toString()) {
      case "name":
        region.setName(value(field.get("fieldValue")));
        break;
      case "belongsArea":
        region.setBusinessArea(value(field.get("fieldValue")));
        break;
      case "TypeOfServiceStation":
        region.setServiceStationType(value(field.get("fieldValue")));
        break;
      case "state":
        region.setCountry(value(field.get("fieldValue")));
        break;
      case "province":
        region.setProvince(value(field.get("fieldValue")));
        break;
      case "city":
        region.setCity(value(field.get("fieldValue")));
        break;
      case "county":
        region.setCounty(value(field.get("fieldValue")));
        break;

      case "fuwuzhanjibie":
        region.setServiceStationLevel(value(field.get("fieldValue")));
        break;

      case "number":
        region.setSn(value(field.get("fieldValue")));
        break;
      case "fuzeren":
        region.setSupervisorId(Integer.parseInt(value(field.get("fieldValue"))));
        region.setSupervisorEmail(value(field.get("userEmail")));
        break;
      case "gongshishiyongchengshi":
        region.setManHourCity(value(field.get("fieldValue")));
        break;

      case "shiyonggongshiguojia":
        region.setManHourCountry(value(field.get("fieldValue")));
        break;

      default:
        log.debug("Unknown filed :" + (new Gson()).toJson(field));
        region.getExtraFields().put(field.get("fieldApiName").toString(),
            new FieldRecord(value(field.get("fieldValue")),
                value(field.get("foreignDataName"), null)));
    }
  }
}
