package com.samoonpride.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineConfig {
    @Getter
    private static String channelToken;

    @Value("${line.bot.channel-token}")
    public void setChannelToken(String token) {
        channelToken = token;
    }
}
