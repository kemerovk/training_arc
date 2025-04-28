package me.project.training_arc.service;

import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @PostConstruct
    public void createInitBucket(){
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("test-bucket").build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("test-bucket").build());
            }
        }
        catch (Exception e) {
            System.out.println("error while creating bucket");
            e.printStackTrace();
        }

    }


    public String uploadFileToMinio(MultipartFile file) throws IOException,
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("test-bucket")
                        .object(file.getOriginalFilename())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );
        return file.getOriginalFilename();

    }

}
