package com.robot.gs.ticket.http.kingdee.model;

import com.robot.gs.ticket.http.kingdee.model.common.QueryNoListData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordQueryNoListModel {
    private QueryNoListData data;
}