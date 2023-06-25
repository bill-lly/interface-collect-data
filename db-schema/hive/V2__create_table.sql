set result_bucket=bigdata-prod-cn-northwest-1-s3-statistics-tahr;


CREATE TABLE IF NOT EXISTS ods_udesk.terminal_user (
  id BIGINT,
  name STRING,
  address STRING,
  customer_grade STRING,
  business_area STRING,
  sn STRING,
  full_name STRING,
  group_id BIGINT,
  group_name STRING,

  province STRING,
  city STRING,
  county STRING,
  customer_sn STRING,

  extra_fields STRING,
  create_ime timestamp,
  update_time timestamp
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/terminal_user';


CREATE TABLE IF NOT EXISTS ods_udesk.work_ticket (
  id BIGINT,
  subject STRING,
  fault_description STRING,
  fault_type STRING,
  fault_symptom STRING,
  sn STRING,
  ticket_type STRING,
  robot_archive_id BIGINT,
  robot_product_id STRING,
  operational_region_name STRING,
  operational_region_id BIGINT,
  fee_type STRING,

  dispatch_time TIMESTAMP,
  dispatch_note STRING,
  contracted_customer_name STRING,
  contracted_customer_id BIGINT,
  province STRING,
  city STRING,
  county STRING,
  status STRING,
  is_under_warranty STRING,
  fae_id BIGINT,
  fae_email STRING,
  robot_model_type_version STRING,
  robot_model_type_version_id BIGINT,
  service_mode STRING,
  service_item_charge DOUBLE,
  service_charge DOUBLE,
  man_hour_charge DOUBLE,
  attachment_charge DOUBLE,
  other_charge DOUBLE,
  total_charge DOUBLE,
  business_area STRING,
  maintenance_category STRING,
  maintenance_second_category STRING,

  appointment_time TIMESTAMP,
  arrival_time TIMESTAMP,
  finish_time TIMESTAMP,
  punch_in_id BIGINT,
  punch_in_location STRING,
  punch_out_id BIGINT,
  punch_out_location STRING,
  business_type STRING,
  maintenance_description STRING,
  work_note STRING,
  approval_status STRING,
  create_user_id BIGINT,
  create_user_email STRING,
  service_charge_stats DOUBLE,
  man_hour_charge_stats DOUBLE,
  other_charge_stats DOUBLE,
  contact_id BIGINT,
  contact_name STRING,
  terminal_user_id BIGINT,
  terminal_user_name STRING,
  man_hour_city STRING,
  man_hour_country STRING,

  extra_fields STRING,
  create_time STRING,
  update_time STRING
) PARTITIONED BY (`sync_date` DATE)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/work_ticket';


CREATE TABLE IF NOT EXISTS ods_udesk.robot_type (
  id BIGINT,
  name STRING,
  note STRING,
  product_sn STRING,
  robot_family_id BIGINT,
  robot_family_code STRING,
  machine_version STRING,
  extra_fields STRING,
  create_time TIMESTAMP,
  update_time TIMESTAMP
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/robot_type';



CREATE TABLE IF NOT EXISTS ods_udesk.customer (
  id BIGINT,
  name STRING,
  sn STRING,
  note STRING,
  grade STRING,
  country String,
  province STRING,
  city STRING,
  county STRING,
  address STRING,
  coordinate STRING,
  customer_type STRING,
  is_efficient_customer STRING,
  business_area STRING,
  customer_nature STRING,
  customer_industry STRING,
  customer_identity STRING,
  extra_fields STRING
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/customer';


CREATE TABLE IF NOT EXISTS ods_udesk.operational_region (
  id INT,
  name STRING,
  business_area STRING,
  service_station_type STRING,
  country STRING,
  province STRING,
  city STRING,
  county STRING,
  service_station_level STRING,
  sn STRING,
  supervisor_id INT,
  supervisor_email STRING,
  man_hour_city STRING,
  man_hour_country STRING,
  extra_fields STRING
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/operational_region';