package com.cjlabs.tgwatcher.business.tg.convert;

import com.cjlabs.tgwatcher.business.tg.mysql.TgGroupAccountMessage;
import com.cjlabs.tgwatcher.business.tg.resp.TgGroupAccountMessageResp;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class TgGroupAccountMessageConvert {

    public static TgGroupAccountMessageResp toResp(TgGroupAccountMessage input) {
        if (Objects.isNull(input)) {
            return null;
        }
        TgGroupAccountMessageResp tgGroupAccountMessageResp = new TgGroupAccountMessageResp();

        tgGroupAccountMessageResp.setId(input.getId());
        tgGroupAccountMessageResp.setBotUsername(input.getBotUsername());
        tgGroupAccountMessageResp.setChatId(input.getChatId());
        tgGroupAccountMessageResp.setChatTitle(input.getChatTitle());
        tgGroupAccountMessageResp.setMessageId(input.getMessageId());
        tgGroupAccountMessageResp.setUpdateId(input.getUpdateId());
        tgGroupAccountMessageResp.setSenderId(input.getSenderId());
        tgGroupAccountMessageResp.setSenderUsername(input.getSenderUsername());
        tgGroupAccountMessageResp.setSenderName(input.getSenderName());
        tgGroupAccountMessageResp.setPhone(input.getPhone());
        tgGroupAccountMessageResp.setAccountNo(input.getAccountNo());
        tgGroupAccountMessageResp.setAccountName(input.getAccountName());
        tgGroupAccountMessageResp.setPasswordPlain(input.getPasswordPlain());
        tgGroupAccountMessageResp.setRemark(input.getRemark());

        return tgGroupAccountMessageResp;
    }

    public static List<TgGroupAccountMessageResp> toResp(List<TgGroupAccountMessage> inputList) {
        if (CollectionUtils.isEmpty(inputList)) {
            return Lists.newArrayList();
        }
        return inputList.stream().map(TgGroupAccountMessageConvert::toResp).collect(Collectors.toList());
    }
}