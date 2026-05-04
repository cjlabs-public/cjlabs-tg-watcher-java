-- TG 群消息解析后的账号记录表
-- 一条 TG 消息里如果包含多组账号信息，则按 record_seq 拆成多行保存。

CREATE TABLE IF NOT EXISTS tg_group_account_message (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    bot_username VARCHAR(128) NULL COMMENT '机器人用户名',
    chat_id BIGINT NOT NULL COMMENT 'TG 群/超群 chatId',
    chat_title VARCHAR(255) NULL COMMENT 'TG 群名称',
    message_id INT NOT NULL COMMENT 'TG 群内消息ID',
    update_id INT NULL COMMENT 'TG updateId',
    record_seq INT NOT NULL DEFAULT 1 COMMENT '同一条消息内第几条账号记录，从1开始',

    sender_id BIGINT NULL COMMENT '发送人TG用户ID',
    sender_username VARCHAR(128) NULL COMMENT '发送人用户名',
    sender_name VARCHAR(255) NULL COMMENT '发送人名称',

    phone VARCHAR(32) NULL COMMENT '手机号',
    account_no VARCHAR(64) NULL COMMENT '银行账号',
    account_name VARCHAR(255) NULL COMMENT '账户姓名',
    password_plain VARCHAR(128) NULL COMMENT '密码明文',
    remark VARCHAR(512) NULL COMMENT '备注信息，例如消息末尾的(2)',

    del_flag      enum ('NORMAL', 'ABNORMAL') default 'NORMAL' not null comment '删除标志',
    create_user   varchar(50)                                  not null comment '创建用户',
    create_date   bigint                                       not null comment '创建时间（UTC毫秒）',
    update_user   varchar(50)                                  null comment '更新用户',
    update_date   bigint                                       not null comment '更新时间（UTC毫秒）',
    trace_id      varchar(64)                 default ''       not null comment '追踪ID',

    PRIMARY KEY (id),
    UNIQUE KEY uk_chat_message_record (chat_id, message_id, record_seq),
    KEY idx_account_no (account_no),
    KEY idx_phone (phone),
    KEY idx_account_name (account_name),
    KEY idx_chat_sent_at (chat_id),
    KEY idx_create_date (create_date)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='TG群消息解析账号记录';
