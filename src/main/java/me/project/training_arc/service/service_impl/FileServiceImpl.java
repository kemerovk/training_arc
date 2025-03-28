package me.project.training_arc.service.service_impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl {

    @Value("${docker.path}")
    private String PATH_TO_VOLUME;

    public void saveFile(MultipartFile file) throws IOException {
        if(file == null) throw new NullPointerException("file is null");
        var fileToSave = new File(PATH_TO_VOLUME + "/" + file.getOriginalFilename());
        Files.copy(file.getInputStream(), fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public File getFile(String filename) {
        return new File(PATH_TO_VOLUME + "/" + filename);
    }
}
