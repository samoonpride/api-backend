package com.samoonpride.backend.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@UtilityClass
public class MediaUtils {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @SneakyThrows
    public static String saveImage(String userId, String messageId) {
        ResponseEntity<byte[]> responseEntity = LineUtils.getMessageContent(messageId);

        log.info("Image response: " + responseEntity);
        byte[] imageContent = responseEntity.getBody();
        String extension = Optional.ofNullable(responseEntity.getHeaders().getContentType())
                .map(MediaType::getSubtype)
                .orElse("jpg");
        log.info("Image extension: " + extension);

        Path imagePath = Path.of("public/images/" + LocalDateTime.now().format(dateTimeFormatter) + "/" + userId);
        String imageFileName = UUID.randomUUID() + "." + extension;

        if (!Files.exists(imagePath)) {
            Files.createDirectories(imagePath);
        }

        Path imageFilePath = imagePath.resolve(imageFileName);
        Files.write(imageFilePath, imageContent);
        log.info("Image saved: " + imageFilePath);

        // remove public from the path
        return imageFilePath.subpath(1, imageFilePath.getNameCount()).toString();
    }

    @SneakyThrows
    public static String saveVideo(String userId, String messageId) {
        ResponseEntity<byte[]> responseEntity = LineUtils.getMessageContent(messageId);

        log.info("Video response: " + responseEntity);
        byte[] imageContent = responseEntity.getBody();
        String extension = Optional.ofNullable(responseEntity.getHeaders().getContentType())
                .map(MediaType::getSubtype)
                .orElse("mp4");
        log.info("Video extension: " + extension);

        Path videoPath = Path.of("public/videos/" + LocalDateTime.now().format(dateTimeFormatter) + "/" + userId);
        String videoFileName = UUID.randomUUID() + "." + extension;

        if (!Files.exists(videoPath)) {
            Files.createDirectories(videoPath);
        }

        Path videoFilePath = videoPath.resolve(videoFileName);
        Files.write(videoFilePath, imageContent);
        log.info("Video saved: " + videoFilePath);

        // remove public from the path
        return videoFilePath.subpath(1, videoFilePath.getNameCount()).toString();
    }
}
