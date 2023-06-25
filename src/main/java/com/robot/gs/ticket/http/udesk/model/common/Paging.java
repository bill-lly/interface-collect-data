package com.robot.gs.ticket.http.udesk.model.common;

import lombok.Data;

@Data
public class Paging {
  private Integer pageNum;
  private Integer pageSize;
  private Integer total;
}
