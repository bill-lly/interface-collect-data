package com.robot.gs.ticket.http.kingdee.model;

import com.robot.gs.ticket.http.kingdee.model.common.QueryViewDetailData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordQuerySessionIdModel {
    private String Acctid;
    private String userName;
    private String password;
    private int lcid;
}
