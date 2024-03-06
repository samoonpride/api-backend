package com.samoonpride.backend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LineConfig {
    @Getter
    private static String channelToken;
    @Getter
    private static String lineWebhookUrl;

    @Value("${line.bot.channel-token}")
    public void setChannelToken(String token) {
        channelToken = token;
    }
    @Value("${line.bot.line-webhook.url}")
    public void setLineWebhookUrl(String url) {
        lineWebhookUrl = url;
    }
}
