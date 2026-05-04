package com.cjlabs.tgwatcher.tg;

import com.cjlabs.core.types.longs.FmkUserId;
import com.cjlabs.core.types.strings.FmkTraceId;
import com.cjlabs.tgwatcher.business.tg.reqsave.TgGroupAccountMessageReqSave;
import com.cjlabs.tgwatcher.business.tg.service.TgGroupAccountMessageService;
import com.cjlabs.tgwatcher.tg.bo.TelegramGroupMessage;
import com.cjlabs.tgwatcher.tg.convert.TelegramGroupMessageConvert;
import com.cjlabs.web.json.FmkJacksonUtil;
import com.cjlabs.web.threadlocal.FmkClientInfo;
import com.cjlabs.web.threadlocal.FmkContextInfo;
import com.cjlabs.web.threadlocal.FmkContextUtil;
import com.cjlabs.web.threadlocal.FmkUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LoggingTelegramGroupMessageListener implements TelegramGroupMessageListener {

    private static final String TSV_HEADER = String.join("\t",
            "chatId", "chatTitle", "messageId", "sentAt", "senderId", "senderName",
            "phone", "accountNo", "accountName", "passwordPlain", "remark");

    private final TelegramAccountMessageParser parser;
    private final TgGroupAccountMessageService messageService;

    public LoggingTelegramGroupMessageListener(
            TelegramAccountMessageParser parser,
            TgGroupAccountMessageService messageService
    ) {
        this.parser = parser;
        this.messageService = messageService;
        log.info("TG_EXCEL_HEADER\t{}", TSV_HEADER);
    }

    @Override
    public void onMessage(TelegramGroupMessage message) {
        boolean contextCreated = initFmkContextIfAbsent(message);
        try {
            doOnMessage(message);
        } finally {
            if (contextCreated) {
                FmkContextUtil.clear();
            }
        }
    }

    private void doOnMessage(TelegramGroupMessage message) {
        log.info(
                "收到 TG 群消息: chatId={}, chatTitle={}, messageId={}, senderId={}, senderName={}, type={}, content={}",
                message.chatId(),
                message.chatTitle(),
                message.messageId(),
                message.senderId(),
                message.senderName(),
                message.mediaType(),
                message.content()
        );

        List<ParsedAccountMessage> accountMessages = parser.parse(message.content());

        log.info("LoggingTelegramGroupMessageListener|onMessage|={}", FmkJacksonUtil.toJson(accountMessages));

        for (ParsedAccountMessage accountMessage : accountMessages) {
            log.info("TG_EXCEL_ROW\t{}", toTsv(message, accountMessage));

            TgGroupAccountMessageReqSave messageReqSave = TelegramGroupMessageConvert.to(message, accountMessage);
            messageService.save(messageReqSave);
        }
    }

    private boolean initFmkContextIfAbsent(TelegramGroupMessage message) {
        if (FmkContextUtil.getContextInfo().isPresent()) {
            return false;
        }

        String requestUri = "tg://chat/" + message.chatId() + "/message/" + message.messageId();
        FmkContextInfo contextInfo = FmkContextInfo.createBasic(FmkTraceId.generate(), requestUri);
        contextInfo.setUserId(FmkUserId.SYSTEM);
        FmkContextUtil.setContextInfo(contextInfo);
        return true;
    }

    private String toTsv(TelegramGroupMessage message, ParsedAccountMessage accountMessage) {
        return String.join("\t",
                cell(message.chatId()),
                cell(message.chatTitle()),
                cell(message.messageId()),
                cell(message.sentAt()),
                cell(message.senderId()),
                cell(message.senderName()),
                cell(accountMessage.phone()),
                cell(accountMessage.accountNo()),
                cell(accountMessage.accountName()),
                cell(accountMessage.passwordPlain()),
                cell(accountMessage.remark()));
    }

    private String cell(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString()
                .replace('\t', ' ')
                .replace('\r', ' ')
                .replace('\n', ' ')
                .trim();
    }
}
