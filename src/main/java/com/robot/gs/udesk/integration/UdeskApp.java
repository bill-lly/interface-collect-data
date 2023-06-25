package com.robot.gs.udesk.integration;

import com.robot.gs.command.CommandArgs;
import com.robot.gs.common.ApiNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.shaded.com.google.protobuf.Api;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static com.robot.gs.udesk.integration.Helper.loadProperties;

@Slf4j
public class UdeskApp {

  private final static String GS_ODS ="gs_ods";

  private final static String WORK_HOURS_USAGE_RECORD = "ods_i_udesk_work_hours_usage_record";
  private final static String SERVICE_ITEM_USAGE_RECORD = "ods_i_udesk_serv_item_usage_record";
  private final static String SERVICE_ITEM_MANAGEMENT = "ods_f_udesk_serv_item_mgmt";
  private final static String TECHNICAL_TRANS_PROJECT_MANAGEMENT = "ods_f_udesk_tech_trans_proj_mgmt";
  private final static String ERROR_CODE = "ods_i_udesk_error_code";
  private final static String REPLACEMENT_PART_USE = "ods_i_udesk_replacement_part_use";
  private final static String CARGO_MANAGEMENT = "ods_i_udesk_cargo_management";

  private CommandArgs args;

  public UdeskApp(CommandArgs args) {
    this.args = args;
  }

  public Integer process() {
    List<ApiRecordUdesk> recordList = createRecordList(args);
    try (HiveDataSource ds = new HiveDataSource("/hive.properties");
         Connection conn = ds.getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.execute("set hive.exec.dynamic.partition=true");
      stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
      stmt.execute("set hive.exec.max.dynamic.partitions.pernode = 1000");
      stmt.execute("set hive.exec.max.dynamic.partitions=1000");
      Properties properties = loadProperties("/udesk.properties");
      for (ApiRecordUdesk apiRecordUdesk : recordList) {
        if (args.hasIntf() && !args.getIntf().equalsIgnoreCase(apiRecordUdesk.getApiName().getName())) {
            continue;
        }
        UdeskClient udeskClient = new UdeskClient(properties, apiRecordUdesk.getApiName(),
            apiRecordUdesk.getFilterIdName(), apiRecordUdesk.getDataProcessor());
        try {
          udeskClient.getAndSaveData(stmt, apiRecordUdesk.getConditionFactory());
        } catch (SQLException e) {
          log.error("Can not save data", e);
          throw new RuntimeException(e);
        }
      }
    } catch (Exception e) {
      log.error("Can not download and save udesk data", e);
      return 1;
    }
    return 0;
  }

  private List<ApiRecordUdesk> createRecordList(CommandArgs args) {
    List<ApiRecordUdesk> apiRecordUdeskList = Arrays.asList(
        new ApiRecordUdesk("terminal.user.group.filter.id",
            ApiNameEnum.TERMINAL_USER_GROUP,
            new TerminalUserGroupProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("archive.filter.id",
            ApiNameEnum.EQUIPMENT_ARCHIVES,
            new RobotArchiveProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("service.station.filter.id",
            ApiNameEnum.SERVICE_STATION,
            new OperationalRegionProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("customer.filter.id",
            ApiNameEnum.CUSTOMER,
            new CustomerProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("robot.model.type.filter.id",
            ApiNameEnum.ROBOT_TYPE,
            new RobotTypeProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("terminal.user.filter.id",
            ApiNameEnum.TERMINAL_USER,
            new TerminalUserProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("work.order.filter.id",
            ApiNameEnum.WORK_TICKET,
            new WorkTicketProcessor(args.getBizDate()),
            new WorkTicketConditionFactory(args.getBizDate())),
        new ApiRecordUdesk("user.info.filter.id",
            ApiNameEnum.USER_INFO,
            new UserInfoProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("fault.category.filter.id",
            ApiNameEnum.FAULT_CATEGORY,
            new FaultCategoryProcessor(args.getBizDate()),
            null),
        new ApiRecordUdesk("work.hours.usage.record",
            ApiNameEnum.WORK_HOURS_USAGE_RECORD,
            new UdeskDataProcessor(ApiNameEnum.WORK_HOURS_USAGE_RECORD, GS_ODS, WORK_HOURS_USAGE_RECORD, args.getBizDate(), true),
            new WorkHoursUsageRecordConditionFactory(args.getBizDate())),
        new ApiRecordUdesk("service.item.usage.record.id",
          ApiNameEnum.SERVICE_ITEM_USAGE_RECORD,
            new UdeskDataProcessor(ApiNameEnum.SERVICE_ITEM_USAGE_RECORD, GS_ODS, SERVICE_ITEM_USAGE_RECORD, args.getBizDate(), true),
            new ServiceItemUsageRecordConditionFactory(args.getBizDate())),
        new ApiRecordUdesk("service.item.management.id",
          ApiNameEnum.SERVICE_ITEM_MANAGEMENT,
            new UdeskDataProcessor(ApiNameEnum.SERVICE_ITEM_MANAGEMENT, GS_ODS, SERVICE_ITEM_MANAGEMENT, args.getBizDate(), true),
            null),
        new ApiRecordUdesk("technical.trans.project.management",
                ApiNameEnum.TECHNICAL_TRANS_PROJECT_MANAGEMENT,
                new UdeskDataProcessor(ApiNameEnum.TECHNICAL_TRANS_PROJECT_MANAGEMENT, GS_ODS, TECHNICAL_TRANS_PROJECT_MANAGEMENT, args.getBizDate(), true),
                null),
        new ApiRecordUdesk("error.code.filter.id",
                ApiNameEnum.Error_Code,
                new ErrorCodeProcessor(ApiNameEnum.Error_Code,GS_ODS,ERROR_CODE,
                args.getBizDate(),true),new ErrorCodeRecordConditionFactory(args.getBizDate())),
        new ApiRecordUdesk("replacement.part.use.filter.id",
                ApiNameEnum.REPLACEMENT_PART_USE,
                new UdeskDataProcessor(ApiNameEnum.REPLACEMENT_PART_USE, GS_ODS, REPLACEMENT_PART_USE, args.getBizDate(), true),
                new ReplacementPartUseFactory(args.getBizDate())),
        new ApiRecordUdesk("cargomanagement.filter.id",
                ApiNameEnum.CARGO_MANAGEMENT,
                new UdeskDataProcessor(ApiNameEnum.CARGO_MANAGEMENT, GS_ODS, CARGO_MANAGEMENT, args.getBizDate(), true),
                new CargoMangementConditionFactory(args.getBizDate()))
);
    return apiRecordUdeskList;
  }


}
