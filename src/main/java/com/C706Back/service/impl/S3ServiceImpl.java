package com.C706Back.service.impl;

import com.C706Back.service.S3Service;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final static String BUCKET = "animatchbucket";

    @Autowired
    private AmazonS3Client s3Client;

    @Override
    public String getObjectUrl(final String key) {
        return String.format("https://%s.s3.amazonaws.com/%s", BUCKET, key);
    }

    @Override
    public String putObject(final MultipartFile multipartFile) {
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = String.format("%s.%s", UUID.randomUUID(), extension);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);
            return key;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteObject(String key) {
        s3Client.deleteObject(BUCKET, key);
    }
}
