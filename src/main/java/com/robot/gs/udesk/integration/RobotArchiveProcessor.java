package com.robot.gs.udesk.integration;

import com.robot.gs.common.DataProcessor;

import static com.robot.gs.udesk.integration.Helper.value;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class RobotArchiveProcessor extends DataProcessor {
  private final RobotArchiveConverter converter = new RobotArchiveConverter();
  private final static String dbName = "aws_task";
  private final static String tbName = "ods_f_udesk_robot_archive";

  public RobotArchiveProcessor(LocalDate startDate) {
    super(startDate);
  }

  public RobotArchiveProcessor() {
  }

  @Override
  public void save(Statement stmt, List<Map<String, Object>> dataList, Boolean isFirst) throws SQLException {
    //获取分区日期
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String pt = formatter.format(startDateTime);

    StringBuilder builder = new StringBuilder(10240);
    if (isFirst) {
      builder.append(String.format("INSERT OVERWRITE TABLE %s.%s\n", dbName, tbName));
    } else {
      builder.append(String.format("INSERT INTO TABLE %s.%s\n", dbName, tbName));
    }
    builder.append("SELECT INLINE (ARRAY ( ");
    for (Map<String, Object> data : dataList) {
      RobotArchive e = converter.from(data);

      builder.append("STRUCT (")
          .append(e.getId()).append(", '")
          .append(e.getProductId()).append("', '")
          .append(value(e.getContractedCustomerName())).append("', ")
          .append(e.getContractedCustomerId()).append(", '")

          .append(value(e.getRobotType())).append("', ")
          .append(e.getRobotTypeId()).append(", '")
          .append(e.getProductionDate()).append("', '")
          .append(e.getWarrantyExpirationDate()).append("', '")
          .append(value(e.getWarrantyStatus())).append("', '")
          .append(e.getDeliveryDate()).append("', '")

          .append(value(e.getBusinessArea())).append("', '")
          .append(value(e.getCountry())).append("', '")
          .append(value(e.getProvince())).append("', '")
          .append(value(e.getCity())).append("', '")
          .append(value(e.getCounty())).append("', '")

          .append(value(e.getFaeName())).append("', '")
          .append(value(e.getFaeEmail())).append("', '")
          .append(value(e.getAddress())).append("', '")
          .append(value(e.getScene())).append("', '")
          .append(value(e.getOperationalRegionName())).append("', ")
          .append(e.getOperationalRegionId()).append(", '")
          .append(value(e.getHasChargingStation())).append("', '")
          .append(value(e.getChargingStation())).append("', '")
          .append(value(e.getIsFinancialLeasing())).append("', '")
          .append(value(e.getFinancialLeasingMode())).append("', '")
          .append(value(e.getOrderType())).append("', '")
          .append(value(e.getManHourCity())).append("', '")
          .append(value(e.getManHourCountry())).append("', '")
          .append(value(e.getRobotName())).append("', '")
          .append(value(e.getCustomerGrade())).append("', '")
          .append(value(e.getLifeCycle())).append("', '")
          .append(value(e.getRobotFamilyCode())).append("', ")

          .append(e.getTerminalUserId()).append(", '")
          .append(value(e.getTerminalUserName())).append("', '")

          .append(value(e.getConsumableSupply())).append("', '")
          .append(value(e.getCleaningMode())).append("', '")
          .append(value(e.getMachineVersion())).append("', '")
          .append(value(e.getPeripherals())).append("', '")
          .append(value(e.getAttachment())).append("', '")
          .append(value(e.getIndoorOrOutdoor())).append("', '")
          .append(value(e.getStatus())).append("', '")
          .append(value(e.getCleaningOrDustMop())).append("', '")

          .append(value(e.getDoesActivate())).append("', '")
          .append(value(e.getInstallationPosition())).append("', '")

          .append(value(e.getLaserTypeCode())).append("', '")
          .append(value(e.getLaserNumber())).append("', '")
          .append(value(e.getMcuVersion())).append("', '")
          .append(value(e.getMcuType())).append("', '")
          .append(value(e.getAppVersion())).append("', '")
          .append(value(e.getRouterVersion())).append("', '")
          .append(value(e.getDoesAssembleNeuralStick())).append("', '")
          .append(value(e.getSystemVersion())).append("', '")
          .append(value(e.getSoftwareVersion())).append("', '")
          .append(value(e.getCameraId())).append("', '")
          .append(value(e.getCameraType())).append("', '")
          .append(value(e.getNote())).append("', ")
          .append(e.getTargetArea()).append(", '")

          .append(value(gson.toJson(e.getExtraFields()))).append("', '")
          .append(e.getCreateTime().format(dateTimeFormatter)).append("', '")
          .append(e.getUpdateTime().format(dateTimeFormatter)).append("', '")
          .append(value(e.getPeripheralList())).append("', '")
          .append(value(e.getDirectClean())).append("', '")
          .append(value(e.getFaePhone())).append("', '")
          .append(value(e.getNoNetworkReason())).append("', '")
          .append(value(e.getProductCarNo())).append("', '")
          .append(value(e.getDeploymentEnvironment())).append("', '")

          .append(pt).append("'), ");
    }
    builder.delete(builder.length() - 2, builder.length())
        .append("))");
    stmt.execute(builder.toString());
    return;
  }
}
