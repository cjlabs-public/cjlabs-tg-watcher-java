package com.cjlabs.tgwatcher.tg.convert;

import com.cjlabs.tgwatcher.business.tg.reqsave.TgGroupAccountMessageReqSave;
import com.cjlabs.tgwatcher.tg.ParsedAccountMessage;
import com.cjlabs.tgwatcher.tg.bo.TelegramGroupMessage;

public class TelegramGroupMessageConvert {

    public static TgGroupAccountMessageReqSave to(TelegramGroupMessage input,
                                                  ParsedAccountMessage message) {
        if (input == null || message == null) {
            return null;
        }

        TgGroupAccountMessageReqSave output = new TgGroupAccountMessageReqSave();
        output.setChatId(input.chatId());
        output.setChatTitle(input.chatTitle());
        output.setMessageId(input.messageId());
        output.setUpdateId(input.updateId());
        output.setSenderId(input.senderId());
        output.setSenderUsername(input.senderUsername());
        output.setSenderName(input.senderName());
        output.setPhone(message.phone());
        output.setAccountNo(message.accountNo());
        output.setAccountName(message.accountName());
        output.setPasswordPlain(message.passwordPlain());
        output.setRemark(message.remark());
        return output;
    }

}
