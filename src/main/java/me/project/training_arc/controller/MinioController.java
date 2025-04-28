package me.project.training_arc.controller;

import io.minio.errors.*;
import me.project.training_arc.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("minio")
public class MinioController {


    @Autowired
    private MinioService minioService;

    @PostMapping("test")
    public String test(@RequestParam("file")MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioService.uploadFileToMinio(file);
    }


}
