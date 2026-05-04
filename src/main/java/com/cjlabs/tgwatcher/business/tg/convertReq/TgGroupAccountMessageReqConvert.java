package com.cjlabs.tgwatcher.business.tg.convertReq;

import com.cjlabs.tgwatcher.business.tg.mysql.TgGroupAccountMessage;
import com.cjlabs.tgwatcher.business.tg.reqquery.TgGroupAccountMessageReqQuery;
import com.cjlabs.tgwatcher.business.tg.reqsave.TgGroupAccountMessageReqSave;
import com.cjlabs.tgwatcher.business.tg.requpdate.TgGroupAccountMessageReqUpdate;

import java.util.Objects;

public class TgGroupAccountMessageReqConvert {

    public static TgGroupAccountMessage toDb(TgGroupAccountMessageReqQuery input) {
        if (Objects.isNull(input)) {
            return null;
        }
        TgGroupAccountMessage tgGroupAccountMessage = new TgGroupAccountMessage();
        
                                        tgGroupAccountMessage.setBotUsername(input.getBotUsername());
                                tgGroupAccountMessage.setChatId(input.getChatId());
                                tgGroupAccountMessage.setChatTitle(input.getChatTitle());
                                tgGroupAccountMessage.setMessageId(input.getMessageId());
                                tgGroupAccountMessage.setUpdateId(input.getUpdateId());
                                tgGroupAccountMessage.setSenderId(input.getSenderId());
                                tgGroupAccountMessage.setSenderUsername(input.getSenderUsername());
                                tgGroupAccountMessage.setSenderName(input.getSenderName());
                                tgGroupAccountMessage.setPhone(input.getPhone());
                                tgGroupAccountMessage.setAccountNo(input.getAccountNo());
                                tgGroupAccountMessage.setAccountName(input.getAccountName());
                                tgGroupAccountMessage.setPasswordPlain(input.getPasswordPlain());
                                tgGroupAccountMessage.setRemark(input.getRemark());
                                                                                                                        
        return tgGroupAccountMessage;
    }

 	public static TgGroupAccountMessage toDb(TgGroupAccountMessageReqUpdate input) {
        if (Objects.isNull(input)) {
            return null;
        }
        TgGroupAccountMessage tgGroupAccountMessage = new TgGroupAccountMessage();
        
                        tgGroupAccountMessage.setId(input.getId());
                                tgGroupAccountMessage.setBotUsername(input.getBotUsername());
                                tgGroupAccountMessage.setChatId(input.getChatId());
                                tgGroupAccountMessage.setChatTitle(input.getChatTitle());
                                tgGroupAccountMessage.setMessageId(input.getMessageId());
                                tgGroupAccountMessage.setUpdateId(input.getUpdateId());
                                tgGroupAccountMessage.setSenderId(input.getSenderId());
                                tgGroupAccountMessage.setSenderUsername(input.getSenderUsername());
                                tgGroupAccountMessage.setSenderName(input.getSenderName());
                                tgGroupAccountMessage.setPhone(input.getPhone());
                                tgGroupAccountMessage.setAccountNo(input.getAccountNo());
                                tgGroupAccountMessage.setAccountName(input.getAccountName());
                                tgGroupAccountMessage.setPasswordPlain(input.getPasswordPlain());
                                tgGroupAccountMessage.setRemark(input.getRemark());
                                                                                                                        
        return tgGroupAccountMessage;
    }
    
    public static TgGroupAccountMessage toDb(TgGroupAccountMessageReqSave input) {
        if (Objects.isNull(input)) {
            return null;
        }
        TgGroupAccountMessage tgGroupAccountMessage = new TgGroupAccountMessage();
        
                                        tgGroupAccountMessage.setBotUsername(input.getBotUsername());
                                tgGroupAccountMessage.setChatId(input.getChatId());
                                tgGroupAccountMessage.setChatTitle(input.getChatTitle());
                                tgGroupAccountMessage.setMessageId(input.getMessageId());
                                tgGroupAccountMessage.setUpdateId(input.getUpdateId());
                                tgGroupAccountMessage.setSenderId(input.getSenderId());
                                tgGroupAccountMessage.setSenderUsername(input.getSenderUsername());
                                tgGroupAccountMessage.setSenderName(input.getSenderName());
                                tgGroupAccountMessage.setPhone(input.getPhone());
                                tgGroupAccountMessage.setAccountNo(input.getAccountNo());
                                tgGroupAccountMessage.setAccountName(input.getAccountName());
                                tgGroupAccountMessage.setPasswordPlain(input.getPasswordPlain());
                                tgGroupAccountMessage.setRemark(input.getRemark());
                                                                                                                        
        return tgGroupAccountMessage;
    }
}