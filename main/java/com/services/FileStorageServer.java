package com.services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class FileStorageServer {
    private static final Logger logger = Logger.getLogger(FileStorageServer.class.getName());
    private final Path fileStorageLocation;

    public FileStorageServer() {
        // 在代码中指定存储目录
        String uploadDir = "D:/F盘/Desktop/软件工程导论/项目实验/OuterProjectFileSaving2/user";
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "-" + fileName;

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // 缩放图像并保存
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            logger.info("Storing file at: " + targetLocation.toString());
            Thumbnails.of(file.getInputStream())
                      .size(150, 180)  // 这里指定目标宽度和高度
                      .toFile(targetLocation.toFile());

            return uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + uniqueFileName + ". Please try again!", ex);
        }
    }

    public Path loadFileAsResource(String fileName) {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        logger.info("Loading file from: " + filePath.toString());
        return filePath;
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
            logger.info("Deleted file: " + filePath.toString());
        } catch (IOException ex) {
            logger.severe("Could not delete file: " + fileName);
            throw new RuntimeException("Could not delete file " + fileName, ex);
        }
    }
}
