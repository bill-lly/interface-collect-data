package com.robot.gs.udesk.integration;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorCodeConverter extends DataConverter<ErrorCode>{
    @Override
    public ErrorCode from(Map<String, Object> data) {
        ErrorCode errorCode = new ErrorCode();
        errorCode.setId(Long.parseLong(data.get("id").toString()));
        List<Map<String, Object>> fieldList = (List<Map<String, Object>>)data.get("fieldDataList");
        for (Map<String, Object> field : fieldList) {
            map(errorCode, field);
        }
        return errorCode;
    }
    private void map(ErrorCode errorCode , Map<String, Object> field) {
        switch (field.get("fieldApiName").toString()) {
            case "name":
                errorCode.setErrorCode(Long.parseLong(field.get("fieldValue").toString()));
                break;
            case "codemingchen":
                errorCode.setErrorName(value(field.get("fieldValue")));
                break;
            case "jiankangdengji":
                errorCode.setHealthLevel(value(field.get("fieldValue")));
                break;

            case "yunweidengji":
                errorCode.setOpLevel(value(field.get("fieldValue")));
                break;
            case "shiyongjixing":
                errorCode.setApplicableModel(value(field.get("optionNameList")));
                break;
            case "beizhu":
                errorCode.setRemarks(value(field.get("fieldValue")));
                break;

            case "createTime":
                errorCode.setCreateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
                        dateTimeFormatter));
                break;

            case "updateTime":
                errorCode.setUpdateTime(LocalDateTime.parse(field.get("fieldValue").toString(),
                        dateTimeFormatter));
                break;

            case "createUser":
                errorCode.setCreateUserId(Long.parseLong(field.get("fieldValue").toString()));
                errorCode.setCreateUserEmail(value(field.get("userEmail")));
                break;

            case "updateUser":
                errorCode.setUpdateUserId(Long.parseLong(field.get("fieldValue").toString()));
                errorCode.setUpdateUserEmail(value(field.get("userEmail")));
                break;

            case "owner":
                errorCode.setOwnerId(Long.parseLong(field.get("fieldValue").toString()));
                errorCode.setOwnerResult(value(field.get("ownerResult").toString()));
                break;

            default:
                log.debug("Unknown filed :" + (new Gson()).toJson(field));
        }
    }
}
