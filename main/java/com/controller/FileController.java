package com.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.logging.Logger;
import java.nio.file.Path;

import com.services.FileStorageServer;

@Controller
public class FileController {
    private static final Logger logger = Logger.getLogger(FileController.class.getName());
    private final FileStorageServer fileStorageService;

    public FileController(FileStorageServer fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        System.out.println("取图片应该");
        try {
            Path filePath = fileStorageService.loadFileAsResource(filename);
            logger.info("Fetching file: " + filePath.toString());
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found " + filename);
            }
        } catch (Exception e) {
            logger.severe("Error loading file: " + e.getMessage());
            throw new RuntimeException("File not found " + filename, e);
        }
    }
}
