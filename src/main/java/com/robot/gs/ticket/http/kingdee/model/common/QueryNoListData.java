package com.robot.gs.ticket.http.kingdee.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
/*
 * kingdee单据查询接口
 * FormId      必传参数-业务对象表单Id
 * FieldKeys       必传参数-需查询的字段key集合
 * FilterString     必传参数-过滤条件
 * OrderString     非必传参数-排序字段
 * TopRowCount     非必传参数-排序字段，0
 * StartRow       非必传参数-开始行索引，0
 * Limit        非必传参数-开始行索引(<=10000)，2000
 * SubSystemId     非必传参数-表单所在的子系统内码
 *
 */

public class QueryNoListData {
    private String FormId;
    private String FieldKeys;
    private String FilterString;
    private String OrderString;
    private int TopRowCount;
    private int StartRow;
    private int Limit;
    private String SubSystemId;
}
