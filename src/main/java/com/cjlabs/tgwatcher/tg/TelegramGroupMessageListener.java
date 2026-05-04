package com.cjlabs.tgwatcher.tg;

import com.cjlabs.tgwatcher.tg.bo.TelegramGroupMessage;

public interface TelegramGroupMessageListener {

    void onMessage(TelegramGroupMessage message);
}
