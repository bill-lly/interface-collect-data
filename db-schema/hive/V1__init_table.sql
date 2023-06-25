set result_bucket=bigdata-prod-cn-northwest-1-s3-statistics-tahr;


CREATE DATABASE IF NOT EXISTS ods_udesk;

CREATE DATABASE IF NOT EXISTS dws_udesk;


CREATE TABLE IF NOT EXISTS ods_udesk.robot_archive (
  id BIGINT,
  product_id STRING,
  contracted_customer_name STRING,
  contracted_customer_id BIGINT,

  robot_type_name STRING,
  robot_type_id BIGINT,
  production_date DATE,
  warranty_expiration_date DATE,
  warranty_status STRING,
  delivery_date DATE,

  business_area STRING,
  country STRING,
  province STRING,
  city STRING,
  county STRING,

  fae_name STRING,
  fae_email STRING,
  address STRING,
  scene STRING,
  operational_region_name STRING,
  operational_region_id BIGINT,
  has_charging_station STRING,
  charging_station STRING,
  is_financial_leasing STRING,
  financial_leasing_mode STRING,
  order_type STRING,
  man_hour_city STRING,
  man_hour_country STRING,
  name STRING,
  customer_grade STRING,
  life_cycle STRING,
  robot_family_code STRING,

  terminal_user_id BIGINT,
  terminal_user_name STRING,

  consumable_supply STRING,
  cleaning_mode STRING,
  machine_version STRING,
  peripherals STRING,
  attachment STRING,
  indoor_or_outdoor STRING,
  status STRING,
  cleaning_or_dust_mop STRING,

  does_activate STRING,
  installation_position STRING,

  laser_type_code STRING,
  laser_number STRING,
  mcu_version STRING,
  mcu_type STRING,
  app_version STRING,
  router_version STRING,
  does_assemble_neural_stick STRING,
  system_version STRING,
  software_version STRING,
  camera_id STRING,
  camera_type STRING,
  note STRING,

  target_area DOUBLE,
  extra_fields STRING,

  create_time TIMESTAMP,
  update_time TIMESTAMP
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/robot_archive';


CREATE TABLE IF NOT EXISTS dws_udesk.customer (
  id BIGINT,
  name STRING,
  update_time TIMESTAMP
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/dws_udesk/customer';


CREATE TABLE IF NOT EXISTS dws_udesk.robot_customer_association (
  robot_id int,
  customer_type string,
  customer_id bigint,
  product_id string
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/dws_udesk/robot_customer_association';

