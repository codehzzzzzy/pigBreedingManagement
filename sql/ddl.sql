-- 创建库
create database if not exists pig_breeding_management;
-- 切换库
use pig_breeding_management;

-- 猪舍表
CREATE TABLE hogring (
    hogring_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '猪舍id 主键',
    hogring_area DECIMAL(10, 2) COMMENT '猪舍面积',
    hogring_capacity INT COMMENT '猪舍容量',
    hogring_status tinyint  COMMENT '猪舍状态(0-干净,1-脏乱)',
    user_id bigint(20) COMMENT '管理人员id',
    pig_id varchar(512) COMMENT '猪只id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='猪舍';

-- 猪只信息表
CREATE TABLE pig (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '猪只id 主键',
    breed varchar(256) COMMENT '品种',
    age INT COMMENT '年龄',
    gender tinyint COMMENT '性别',
    weight DECIMAL(10, 2) COMMENT '体重',
    health tinyint COMMENT '健康状态(0-不健康;1-亚健康;2-健康)',
    feed_type varchar(50) COMMENT'饲料种类',
    hogring_id INT COMMENT'所在猪舍id',
    status tinyint COMMENT '是否出库(0-未出库;1-已出库)'
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='猪只信息';

-- 饲料管理表
CREATE TABLE feed_management (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '饲料id 主键',
    feed_type VARCHAR(50) COMMENT '饲料种类',
    hogring_id INT COMMENT '使用猪舍',
    feed_amount DECIMAL(10, 2) COMMENT '饲料用量',
    feeder bigint(20)  COMMENT '管理人员id'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='饲料管理';

-- 猪只进出库管理表
CREATE TABLE pig_in_out (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '进出记录id 主键',
    pig_id INT COMMENT '猪只id',
    hogring_id INT COMMENT '进入猪舍id',
    in_time datetime COMMENT '进入时间',
    out_time datetime COMMENT '出库时间',
    source VARCHAR(50) COMMENT '猪只来源',
    destination VARCHAR(50) COMMENT '猪只去向'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='猪只进出库管理';

-- 管理员表
create table user
(
    id              bigint(20)   auto_increment comment 'id' primary key,
    account         varchar(256)                           not null comment '账号',
    pwd             varchar(512)                           not null comment '密码',
    create_time     datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time     datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete       tinyint      default 0                 not null comment '是否删除(0.不删除;1.删除)',
    type            tinyint      default 1                 not null comment '管理员类别(1.总管理员;2.猪舍管理员;3.猪只信息管理员;4.饲料管理员;5.进出库管理员)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin comment '管理员';