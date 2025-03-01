package me.project.training_arc.controller;


import me.project.training_arc.service_impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


@RestController
@RequestMapping("files")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @GetMapping
    public String ok(){
        return "ok";
    }



    @PostMapping("/upload-files")
    public boolean uploadFile(@RequestParam MultipartFile file){
        try {
            fileService.saveFile(file);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) {
        try{
            var file = fileService.getFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=/" + filename + "/")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(file.toPath())));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
