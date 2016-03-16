drop index Unic_Index_1 on gen_plugin;
drop table if exists gen_plugin;
/*==============================================================*/
/* Table: gen_plugin                                            */
/*==============================================================*/
create table gen_plugin
(
   ID                   varchar(32) not null comment '主键',
   GUN_TYPE             varchar(1) not null comment '生成类型(0:xml, 1:java)',
   SRC_PARENT           varchar(255) not null comment '源文件名',
   SRC_FIELD            varchar(255) not null comment '源文件字段',
   TAR_PARENT           varchar(255) not null comment '目标文件名',
   TAR_FIELD            varchar(255) not null comment '目标文件字段',
   primary key (ID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table gen_plugin comment '代码生成基础表';
/*==============================================================*/
/* Index: Unic_Index_1                                          */
/*==============================================================*/
create unique index Unic_Index_1 on gen_plugin
(
   GUN_TYPE,
   SRC_PARENT,
   SRC_FIELD
);
