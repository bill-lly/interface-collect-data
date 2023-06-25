package com.robot.gs.udesk.integration;

import static com.robot.gs.udesk.integration.Helper.value;
import static com.robot.gs.udesk.integration.Helper.dateFormatter;
import static com.robot.gs.udesk.integration.Helper.intValue;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.robot.gs.common.DataProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkTicketProcessor extends DataProcessor {
  private final WorkTicketConverter converter = new WorkTicketConverter();
  private final static String dbName = "gs_ods";
  private final static String tbName = "ods_i_udesk_work_ticket";

  public WorkTicketProcessor(LocalDate startDate) {
    super(startDate);
  }

  public WorkTicketProcessor() {
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst)
      throws SQLException {
    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("PARTITION (pt)\n")
        .append("SELECT INLINE (ARRAY ( ");
    boolean doFinish = false;
    for (Map<String, Object> data : dataList) {
      WorkTicket e = converter.from(data);

      if (e.updateTime.isBefore(startDateTime)) {
        doFinish = true;
        continue;
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(value(e.getSubject())).append("', '")
          .append(value(e.getFaultDescription())).append("', '")
          .append(value(e.getFaultType())).append("', '")
          .append(value(e.getFaultSymptom())).append("', '")
          .append(value(e.getSn())).append("', '")
          .append(value(e.getTicketType())).append("', ")
          .append(e.getRobotArchiveId()).append(", '")
          .append(value(e.getRobotProductId())).append("', '")
          .append(value(e.getOperationalRegionName())).append("', ")

          .append(e.getOperationalRegionId()).append(", '")
          .append(value(e.getFeeType())).append("', '")
          .append(null == e.getDispatchTime() ? "1970-01-01 00:00:00" :
              e.getDispatchTime().format(dateTimeFormatter)).append("', '")
          .append(value(e.getDispatchNote())).append("', '")
          .append(value(e.getContractedCustomerName())).append("', ")
          .append(e.getContractedCustomerId()).append(", '")
          .append(value(e.getProvince())).append("', '")
          .append(value(e.getCity())).append("', '")
          .append(value(e.getCounty())).append("', '")
          .append(value(e.getStatus())).append("', '")

          .append(value(e.getIsUnderWarranty())).append("', ")
          .append(e.getFaeId()).append(", '")
          .append(value(e.getFaeEmail())).append("', '")
          .append(value(e.getRobotModelTypeVersion())).append("', ")
          .append(e.getRobotModelTypeVersionId()).append(", '")
          .append(value(e.getServiceMode())).append("', ")
          .append(e.getServiceItemCharge()).append(", ")
          .append(e.getServiceCharge()).append(", ")
          .append(e.getManHourCharge()).append(", ")
          .append(e.getAttachmentCharge()).append(", ")
          .append(e.getOtherCharge()).append(", ")


          .append(e.getTotalCharge()).append(", '")
          .append(value(e.getBusinessArea())).append("', '")
          .append(value(e.getMaintenanceCategory())).append("', '")
          .append(value(e.getMaintenanceSecondCategory())).append("', '")
          .append(null == e.getAppointmentTime() ? "1970-01-01 00:00:00" :
              e.getAppointmentTime().format(dateTimeFormatter)).append("', '")
          .append(null == e.getArrivalTime() ? "1970-01-01 00:00:00" :
              e.getArrivalTime().format(dateTimeFormatter)).append("', '")
          .append(null == e.getFinishTime() ? "1970-01-01 00:00:00" :
              e.getFinishTime().format(dateTimeFormatter)).append("', ")
          .append(e.getPunchInId()).append(", '")
          .append(value(e.getPunchInLocation())).append("', ")
          .append(e.getPunchOutId()).append(", '")

          .append(value(e.getPunchOutLocation())).append("', '")
          .append(value(e.getBusinessType())).append("', '")
          .append(value(e.getMaintenanceDescription())).append("', '")
          .append(value(e.getWorkNote())).append("', '")
          .append(value(e.getApprovalStatus())).append("', ")
          .append(e.getCreateUserId()).append(", '")
          .append(value(e.getCreateUserEmail())).append("', ")
          .append(e.getServiceChargeStats()).append(", ")
          .append(e.getManHourChargeStats()).append(", ")
          .append(e.getOtherChargeStats()).append(", ")

          .append(e.getContactId()).append(", '")
          .append(value(e.getContactName())).append("', ")
          .append(e.getTerminalUserId()).append(", '")
          .append(value(e.getTerminalUserName())).append("', '")
          .append(value(e.getManHourCity())).append("', '")
          .append(value(e.getManHourCountry())).append("', '")
          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(e.getCreateTime().format(dateTimeFormatter)).append("', '")
          .append(e.getUpdateTime().format(dateTimeFormatter)).append("', '")
          .append(intValue(e.getDeliverySpent())).append("', '")
          .append(dateFormatter(e.getDeliveryDate(), dateTimeFormatter)).append("', '")
          .append(dateFormatter(e.getProcessUpdateTime(), dateTimeFormatter)).append("', '")
          .append(value(e.getModificationCode())).append("', '")
          .append(value(e.getProcessingProgress())).append("', '")
          .append(value(e.getWhetherUnbox())).append("', '")
          .append(value(e.getModificationClass())).append("', '")
          .append(value(e.getCustomerGroup())).append("', '")
          .append(value(e.getCustomerClass())).append("', '")
          .append(value(e.getDataSource())).append("', '")
          .append(value(e.getProductLine())).append("', '")
          .append(e.getIsDelete()).append("', '")
          .append(value(e.getFaultConfirm())).append("', '")
          .append(value(e.getSoftwareVersionTicket())).append("', '")
          .append(startDateTime.format(formatter)).append("'), ");
    }
    if (builder.toString().endsWith(", ")) {
      builder.delete(builder.length() - 2, builder.length())
          .append("))");
      stmt.execute(builder.toString());
    }

//    if (doFinish) {
//      return Integer.MAX_VALUE;
//    }
    return;
  }

}
