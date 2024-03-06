package com.samoonpride.backend.utils;

import com.samoonpride.backend.config.WebClientConfig;
import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@UtilityClass
public class LineUtils {

    // Get image content from the line api
    public static ResponseEntity<byte[]> getMessageContent(String messageId) {
        URI uri = URI.create("https://api-data.line.me/v2/bot/message/" + messageId + "/content");
        return WebClientConfig.getWebClient().get()
                .uri(uri)
                .retrieve()
                .toEntity(byte[].class)
                .block();
    }
}
