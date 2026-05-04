package com.cjlabs.tgwatcher.tg.bo;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chat.Chat;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.time.Instant;
import java.util.Objects;

public record TelegramGroupMessage(
        Integer updateId,
        Integer messageId,
        Integer messageThreadId,
        Long chatId,
        String chatType,
        String chatTitle,
        Long senderId,
        String senderUsername,
        String senderName,
        Instant sentAt,
        String text,
        String caption,
        String mediaType,
        boolean command
) {

    public static TelegramGroupMessage from(Update update, Message message) {
        Chat chat = message.getChat();
        User from = message.getFrom();
        String text = message.getText();
        String caption = message.getCaption();

        return new TelegramGroupMessage(
                update.getUpdateId(),
                message.getMessageId(),
                message.getMessageThreadId(),
                chat == null ? null : chat.getId(),
                chat == null ? null : chat.getType(),
                chat == null ? null : chat.getTitle(),
                from == null ? null : from.getId(),
                from == null ? null : from.getUserName(),
                senderName(from),
                message.getDate() == null ? null : Instant.ofEpochSecond(message.getDate()),
                text,
                caption,
                mediaType(message),
                message.isCommand()
        );
    }

    public String content() {
        if (text != null && !text.isBlank()) {
            return text;
        }
        if (caption != null && !caption.isBlank()) {
            return caption;
        }
        return "[" + mediaType + "]";
    }

    private static String senderName(User from) {
        if (from == null) {
            return null;
        }
        return String.join(" ",
                Objects.toString(from.getFirstName(), ""),
                Objects.toString(from.getLastName(), "")
        ).trim();
    }

    private static String mediaType(Message message) {
        if (message.hasText()) {
            return "text";
        }
        if (message.hasPhoto()) {
            return "photo";
        }
        if (message.hasVideo()) {
            return "video";
        }
        if (message.hasDocument()) {
            return "document";
        }
        if (message.hasAudio()) {
            return "audio";
        }
        if (message.hasVoice()) {
            return "voice";
        }
        if (message.hasAnimation()) {
            return "animation";
        }
        if (message.hasSticker()) {
            return "sticker";
        }
        if (message.hasLocation()) {
            return "location";
        }
        if (message.hasContact()) {
            return "contact";
        }
        if (message.hasPoll()) {
            return "poll";
        }
        return "unknown";
    }
}
