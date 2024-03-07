package com.samoonpride.backend.utils;

import com.samoonpride.backend.config.ImageConfig;
import com.samoonpride.backend.enums.MediaEnum;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Log4j2
@UtilityClass
public class ThumbnailUtils {
    @SneakyThrows
    public String createThumbnail(File file, MediaEnum type) {
        log.info("Creating thumbnail for file: " + file.getName());
        File thumbnailFile = new File("public/thumbnail/" + "thumbnail_" + file.getName());
        try {
            if (!thumbnailFile.exists()) {
                // create directory if not exists
                if (!thumbnailFile.getParentFile().exists()) {
                    thumbnailFile.getParentFile().mkdirs();
                }
                if (type == MediaEnum.IMAGE) {
                    Thumbnails.of(file)
                            .size(ImageConfig.getThumbnailWidth(), ImageConfig.getThumbnailHeight())
                            .keepAspectRatio(true)
                            .outputFormat("jpeg")
                            .toFile(thumbnailFile);
                } else {
                    // If it's a video, use FFmpeg to extract a frame and generate a thumbnail
                    String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                    thumbnailFile = new File("public/thumbnail/" + "thumbnail_" + file.getName().replace(extension, "jpeg"));
                    ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-i", file.getAbsolutePath(), "-ss", "00:00:01", "-vframes", "1", "-vf", "scale=320:-1", "-q:v", "2", thumbnailFile.getAbsolutePath());
                    Process process = processBuilder.start();
                    process.waitFor();
                }
            }
        } catch (IOException e) {
            log.error("Error creating thumbnail: " + e.getMessage());
        }
        log.info("Thumbnail created: " + thumbnailFile.getName());

        // remove public from the path
        Path path = thumbnailFile.toPath().subpath(1, thumbnailFile.toPath().getNameCount());
        return path.toString();
    }
}
