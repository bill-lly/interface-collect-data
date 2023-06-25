set result_bucket=bigdata-prod-cn-northwest-1-s3-statistics-tahr;

CREATE TABLE IF NOT EXISTS ods_udesk.terminal_user_group (
  id INT,
  name STRING,
  sn STRING,
  customer_grade STRING,
  bargain_mode STRING,
  terminal_industry STRING,
  extra_fields STRING
)
ROW FORMAT delimited fields terminated by ','
STORED AS orc
LOCATION 's3://${hiveconf:result_bucket}/user/hive/warehouse/ods_udesk/terminal_user_group';