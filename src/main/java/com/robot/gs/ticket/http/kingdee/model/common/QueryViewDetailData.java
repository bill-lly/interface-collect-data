package com.robot.gs.ticket.http.kingdee.model.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryViewDetailData {
    private int CreateOrgId;
    private String Number;
    private String Id;
    private String IsSortBySeq;
}
