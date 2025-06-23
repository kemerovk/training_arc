package me.project.training_arc.controller;

import io.minio.errors.*;
import me.project.training_arc.service.ClientService;
import me.project.training_arc.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("minio")
public class MinioController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MinioService minioService;

    @PostMapping("upload")
    public String test(@RequestParam("bucket") String bucketName, @RequestParam("file")MultipartFile file) throws
            ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException
    {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("login: " + login);
        String name = minioService.uploadFileToMinio(bucketName, file);
        if (bucketName.equals("avatar")){
            clientService.updateMinioPath(login, bucketName);
        }
        return name;

    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String bucket,
                                                            @RequestParam String object) {
        InputStream fileStream = minioService.downloadFile(bucket, object);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + object + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(fileStream));
    }


}
