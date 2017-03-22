CREATE TABLE JOB_EXECUTION_LOG
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  job_name VARCHAR(100) NOT NULL,
  task_id VARCHAR(255) NOT NULL,
  hostname VARCHAR(255) NOT NULL,
  ip VARCHAR(50) NOT NULL,
  sharding_item INT(11) NOT NULL,
  execution_source VARCHAR(20) NOT NULL,
  failure_cause VARCHAR(4000),
  is_success BIT(1) NOT NULL,
  start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  complete_time TIMESTAMP
);
CREATE TABLE JOB_STATUS_TRACE_LOG
(
  id VARCHAR(40) PRIMARY KEY NOT NULL,
  job_name VARCHAR(100) NOT NULL,
  original_task_id VARCHAR(255) NOT NULL,
  task_id VARCHAR(255) NOT NULL,
  slave_id VARCHAR(50) NOT NULL,
  source VARCHAR(50) NOT NULL,
  execution_type VARCHAR(20) NOT NULL,
  sharding_item VARCHAR(100) NOT NULL,
  state VARCHAR(20) NOT NULL,
  message VARCHAR(4000),
  creation_time TIMESTAMP
);
CREATE TABLE dd_region
(
  id VARCHAR(9) PRIMARY KEY NOT NULL,
  name VARCHAR(128) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL,
  parent_id VARCHAR(6) NOT NULL
);
CREATE TABLE it_item
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  shop_id VARCHAR(32) NOT NULL,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(300) NOT NULL,
  price BIGINT(20) NOT NULL,
  stock INT(11) NOT NULL,
  sales_volume INT(11) NOT NULL,
  fav_volume INT(11) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL,
  delected VARCHAR(1) DEFAULT '0' NOT NULL,
  comments_volume INT(11) NOT NULL
);
CREATE TABLE it_item_attr
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  item_id VARCHAR(32) NOT NULL,
  name VARCHAR(50) NOT NULL,
  value VARCHAR(250) NOT NULL,
  sequence INT(11) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE it_item_cover
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  item_id VARCHAR(32) NOT NULL,
  cover VARCHAR(200) NOT NULL,
  sequence INT(11) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE it_item_detail
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  item_id VARCHAR(32) NOT NULL,
  detail VARCHAR(200) NOT NULL,
  sequence INT(11) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE it_shop
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  name VARCHAR(50) NOT NULL,
  location VARCHAR(3) NOT NULL,
  describing_rate INT(11) NOT NULL,
  service_rate INT(11) NOT NULL,
  delivery_rate INT(11) NOT NULL,
  comprehensive_rate INT(11) NOT NULL,
  support_cod VARCHAR(1) NOT NULL,
  support_online_pay VARCHAR(1) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL,
  locked VARCHAR(1) DEFAULT '1' NOT NULL
);
CREATE TABLE mb_account
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  email VARCHAR(48),
  email_activated INT(1) NOT NULL,
  mobile VARCHAR(11),
  mobile_activated INT(1) NOT NULL,
  region VARCHAR(15) NOT NULL,
  avatar VARCHAR(120) NOT NULL,
  password VARCHAR(40) NOT NULL,
  random_token VARCHAR(16) NOT NULL,
  lock_release_time DATETIME(3),
  delected INT(1) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL,
  nickname VARCHAR(16) NOT NULL
);
CREATE TABLE mb_behavior_trace
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  account_id VARCHAR(32) NOT NULL,
  description VARCHAR(50) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_account_map_permission
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  account_id VARCHAR(32) NOT NULL,
  permission_id VARCHAR(32) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_account_map_role
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  account_id VARCHAR(32) NOT NULL,
  role_id VARCHAR(32) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_menu
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  description VARCHAR(32) NOT NULL,
  name VARCHAR(16) NOT NULL,
  link VARCHAR(120) NOT NULL,
  level INT(11) NOT NULL,
  structure VARCHAR(20) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_permission
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  description VARCHAR(32) NOT NULL,
  data_pms INT(11),
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_role
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  description VARCHAR(16) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_role_map_menu
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  role_id VARCHAR(32) NOT NULL,
  menu_id VARCHAR(32) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE pv_role_map_permission
(
  id VARCHAR(32) PRIMARY KEY NOT NULL,
  role_id VARCHAR(32) NOT NULL,
  permission_id VARCHAR(32) NOT NULL,
  updated_person VARCHAR(32) NOT NULL,
  updated_timestamp DATETIME(3) NOT NULL,
  created_person VARCHAR(32) NOT NULL,
  created_timestamp DATETIME(3) NOT NULL
);
CREATE TABLE tc_extension_log
(
  id INT(11) PRIMARY KEY NOT NULL COMMENT '主键' AUTO_INCREMENT,
  unique_key VARCHAR(64) COMMENT '业务键',
  level VARCHAR(5) NOT NULL COMMENT '级别',
  date DATETIME(3) NOT NULL COMMENT '日期',
  logger VARCHAR(40) NOT NULL COMMENT 'Logger名',
  thread VARCHAR(40) NOT NULL COMMENT '线程名',
  file VARCHAR(40) NOT NULL COMMENT '文件名',
  line INT(11) NOT NULL COMMENT '行数',
  msg TEXT NOT NULL COMMENT '信息',
  exception TEXT COMMENT '异常堆栈'
);
CREATE INDEX TASK_ID_STATE_INDEX ON JOB_STATUS_TRACE_LOG (task_id, state);
CREATE INDEX dd_region_name_index ON dd_region (name);
CREATE UNIQUE INDEX it_item_name_uidx ON it_item (title);
CREATE INDEX it_item_spid_idx ON it_item (shop_id);
CREATE INDEX it_item_attr_itmid_idx ON it_item_attr (item_id);
CREATE INDEX it_item_cover_itmid_idx ON it_item_cover (item_id);
CREATE INDEX it_item_detail_itmid_idx ON it_item_detail (item_id);
CREATE UNIQUE INDEX it_shop_name_uidx ON it_shop (name);
CREATE INDEX mb_account_email_index ON mb_account (email);
CREATE INDEX mb_account_mobile_index ON mb_account (mobile);
CREATE UNIQUE INDEX mb_account_name_uindex ON mb_account (nickname);
CREATE UNIQUE INDEX mb_account_random_token_uindex ON mb_account (random_token);
CREATE INDEX mb_behavior_trace_account_id_index ON mb_behavior_trace (account_id);
CREATE UNIQUE INDEX pv_account_permission_uindex ON pv_account_map_permission (account_id, permission_id);
CREATE UNIQUE INDEX pv_account_role_uindex ON pv_account_map_role (account_id, role_id);
CREATE UNIQUE INDEX pv_role_menu_unique_index ON pv_role_map_menu (role_id, menu_id);
CREATE UNIQUE INDEX pv_role_permission_uindex ON pv_role_map_permission (role_id, permission_id);