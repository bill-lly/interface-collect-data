package com.robot.gs.ticket.http.udesk.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.robot.gs.ticket.http.udesk.constant.ApiConstant;
import com.robot.gs.ticket.http.udesk.enums.archive.ArchiveFieldDataEnum;
import com.robot.gs.ticket.http.udesk.enums.order.WorkOrderFieldDataEnum;
import com.robot.gs.ticket.http.udesk.model.common.FieldData;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public final class DataUtil {

  private DataUtil() {

  }

  private static final ImmutableMap<String, String> regionMapping =
      new ImmutableMap.Builder<String, String>()
      .put("APAC", "亚太区")
      .put("TRANSATLANTIC", "欧美区")
      .put("亚太", "亚太区")
      .put("其他", "其他")
      .put("华东", "华东区")
      .put("华中", "华中区")
      .put("华北", "华北区")
      .put("华南", "华南区")
      .put("欧美", "欧美区")
      .put("海外", "海外直属")
      .put("西部", "西部区")
      .build();
  private static final ImmutableMap<String, String> periperialMapping =
      new ImmutableMap.Builder<String, String>()
      .put("[\"No\"]", "无")
      .put("[\"充电桩\",\"工作站\",\"梯控\"]", "工作站，充电桩")
      .put("[\"充电桩\",\"工作站\"]", "工作站，充电桩")
      .put("[\"充电桩\",\"无\"]", "充电桩")
      .put("[\"充电桩\",\"梯控\",\"无\"]", "充电桩，梯控")
      .put("[\"充电桩\",\"梯控\"]", "充电桩，梯控")
      .put("[\"充电桩\"]", "充电桩")
      .put("[\"工作站\",\"充电桩\",\"梯控\"]", "工作站，充电桩，梯控")
      .put("[\"工作站\",\"充电桩\"]", "工作站，充电桩")
      .put("[\"工作站\",\"无\"]", "工作站")
      .put("[\"工作站\",\"梯控\"]", "工作站，梯控")
      .put("[\"工作站\"]", "工作站")
      .put("[\"无\"]", "无")
      .put("[\"梯控\",\"工作站\"]", "工作站，梯控")
      .put("[\"梯控\"]", "梯控")
      .put("[]", "无")
      .build();
  private static final ImmutableList codeList =
      ImmutableList.of("40", "50", "60", "75", "111", "112", "X");


  public static Map<Object, Object> convertToMap(Object data) {
    Preconditions.checkArgument(data instanceof Map<?, ?>, "[DataUtil] data struct is not map");
    return (Map<Object, Object>) data;
  }

  public static List convertToList(Object data) {
    Preconditions.checkArgument(data instanceof List<?>, "[DataUtil] data struct is not list");
    return (List<Object>) data;
  }

  public static Map<String, Optional> setToMap(Set<String> queryKey) {
    Preconditions
        .checkArgument(!CollectionUtils.isEmpty(queryKey), "[DataUtil] input set is empty");
    return queryKey.stream().collect(Collectors.toMap(key -> key, e -> Optional.empty()
    ));
  }

  public static Map<String, Optional> dataValue(Set<String> queryParamsSet, final Object data) {
    Preconditions.checkArgument(data instanceof Map<?, ?>, "[DataUtil] data struct is not map");
    Map<String, Optional> dataValue = setToMap(queryParamsSet);
    Map<Object, Object> map = convertToMap(data);
    List<Map<Object, Object>> fieldDataList = convertToList(map.get(ApiConstant.FIELAD_LIST));
    dataValue.entrySet().forEach(entry -> {
      fieldDataList.forEach(fieldData -> {
        String fieldApiName = (String) fieldData.get(ApiConstant.FIELD_API_NAME);
        if (entry.getKey().equals(fieldApiName)) {
          if (WorkOrderFieldDataEnum.ASSIGNEE.getKey().equals(fieldApiName)) {
            entry.setValue(
                Optional.ofNullable(String.valueOf(fieldData.get(ApiConstant.USER_EMAIL))));
          } else if (ArchiveFieldDataEnum.STATION_NAME.getKey().equals(fieldApiName)) {
            entry.setValue(
                Optional.ofNullable(String.valueOf(fieldData.get(ApiConstant.FOREIGN_DATA_NAME))));
          } else {
            entry.setValue(
                Optional.ofNullable(String.valueOf(fieldData.get(ApiConstant.FIELD_VALUE))));
          }

        }
      });
    });
    if (map.containsKey(ApiConstant.ID)) {
      dataValue.put(ApiConstant.ID, Optional.ofNullable(String.valueOf(map.get(ApiConstant.ID))));
    }

    return dataValue;
  }

  public static List<FieldData> commonFieldData(Map<String, Optional> paramMap) {
    Preconditions
        .checkArgument(!CollectionUtils.isEmpty(paramMap),
            "[DataUtil] data paramMap is empty");
    return paramMap.entrySet().stream().filter(e -> e.getValue().isPresent() && ObjectUtils
        .isNotEmpty(e.getValue().get())).map(entry -> {
          return FieldData.builder()
          .fieldApiName(entry.getKey())
          .fieldValue(String.valueOf(entry.getValue().get()))
          .build();
        }).collect(Collectors.toList());
  }

  public static FieldData specialFieldData(String key, String value, String externalFielName) {
    return FieldData.builder()
        .fieldApiName(key)
        .fieldValue(value)
        .foreignExternalFieldApiName(externalFielName)
        .build();
  }

  public static Map<String, String> optionMapConvert(Map<String, Optional> inputMap) {
    return inputMap.entrySet().stream().collect(Collectors.toMap(
        entry -> entry.getKey(),
        entry -> String.valueOf(entry.getValue().map(p -> p).orElse(StringUtils.EMPTY)
        )));
  }

  public static Optional<String> convertRegion(String region) {
    if (!regionMapping.containsKey(region) || StringUtils.isEmpty(region)) {
      return Optional.empty();

    }
    return Optional.of(regionMapping.get(region));
  }

  public static Optional<String> convertPeriperial(String periperial) {
    if (!periperialMapping.containsKey(periperial) || StringUtils.isEmpty(periperial)) {
      return Optional.empty();

    }
    return Optional.of(periperialMapping.get(periperial));
  }

  public static Boolean isValidCode(String robotFamilyCode) {
    return codeList.contains(robotFamilyCode);
  }
}
