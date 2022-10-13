package com.C706Back.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String getObjectUrl(String key);

    String putObject(MultipartFile multipartFile);

    void deleteObject(String key);
}
