package com.cjlabs.tgwatcher.business.tg.resp;

import lombok.Data;

import java.time.Instant;

/**
 * tg_group_account_message TG群消息解析账号记录
 * <p>
 * 2026-05-04 23:35:56
 */
@Data
public class TgGroupAccountMessageResp {


    /**
     * 主键ID
     */
    private Long id;

    /**
     * 机器人用户名
     */
    private String botUsername;

    /**
     * TG 群/超群 chatId
     */
    private Long chatId;

    /**
     * TG 群名称
     */
    private String chatTitle;

    /**
     * TG 群内消息ID
     */
    private Integer messageId;

    /**
     * TG updateId
     */
    private Integer updateId;

    /**
     * 发送人TG用户ID
     */
    private Long senderId;

    /**
     * 发送人用户名
     */
    private String senderUsername;

    /**
     * 发送人名称
     */
    private String senderName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 银行账号
     */
    private String accountNo;

    /**
     * 账户姓名
     */
    private String accountName;

    /**
     * 密码明文
     */
    private String passwordPlain;

    /**
     * 备注信息，例如消息末尾的(2)
     */
    private String remark;


}