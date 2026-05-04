package com.cjlabs.tgwatcher.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Configuration
@ConfigurationProperties(prefix = "tg")
public class TelegramProperties {

    private boolean enabled = true;

    private String botId;

    private String botName;

    private String username;

    private String token;

    private String mode = "polling";

    private boolean dropPendingUpdates;

    private String commandPrefix = "/";
}
