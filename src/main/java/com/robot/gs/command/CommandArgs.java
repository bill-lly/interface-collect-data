package com.robot.gs.command;

import com.robot.gs.common.ModuleEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class CommandArgs {

  private ModuleEnum module;

  private String intf;

  private LocalDate bizDate = LocalDate.now().minusDays(1);

  public CommandArgs(String module, String intf, String bizDate) {
    this.module = ModuleEnum.valueOf(StringUtils.upperCase(module));
    this.intf = intf;
    if (bizDate != null) {
      this.bizDate = LocalDate.parse(bizDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
  }

  public boolean hasIntf() {
    return getIntf() != null;
  }

  public boolean hasBizDate() {
    return getBizDate() != null;
  }
}
