package com.cjlabs.tgwatcher.tg;

import com.cjlabs.tgwatcher.config.TelegramProperties;
import com.cjlabs.tgwatcher.tg.bo.TelegramGroupMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "tg", name = "enabled", havingValue = "true")
public class TelegramGroupMessageBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private static final Logger log = LoggerFactory.getLogger(TelegramGroupMessageBot.class);

    private final TelegramProperties properties;
    private final List<TelegramGroupMessageListener> listeners;

    public TelegramGroupMessageBot(TelegramProperties properties, List<TelegramGroupMessageListener> listeners) {
        this.properties = properties;
        this.listeners = listeners;
        validateProperties(properties);
    }

    @Override
    public String getBotToken() {
        return properties.getToken();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        Message message = getGroupMessage(update);
        if (message == null) {
            return;
        }

        TelegramGroupMessage groupMessage = TelegramGroupMessage.from(update, message);
        for (TelegramGroupMessageListener listener : listeners) {
            try {
                listener.onMessage(groupMessage);
            } catch (RuntimeException e) {
                log.error("处理 TG 群消息失败: updateId={}, chatId={}, messageId={}",
                        groupMessage.updateId(), groupMessage.chatId(), groupMessage.messageId(), e);
            }
        }
    }

    private Message getGroupMessage(Update update) {
        Message message = null;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else if (update.hasEditedMessage()) {
            message = update.getEditedMessage();
        }

        if (message == null || message.getChat() == null) {
            return null;
        }

        Chat chat = message.getChat();
        boolean groupChat = Boolean.TRUE.equals(chat.isGroupChat()) || Boolean.TRUE.equals(chat.isSuperGroupChat());
        if (!groupChat) {
            return null;
        }

        return message;
    }

    private static void validateProperties(TelegramProperties properties) {
        if (!"polling".equalsIgnoreCase(properties.getMode())) {
            throw new IllegalStateException("当前代码只支持 tg.mode=polling");
        }
        if (!StringUtils.hasText(properties.getToken())) {
            throw new IllegalStateException("请在 yml 中配置 tg.token");
        }
    }
}
