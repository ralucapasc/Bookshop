package com.pitech.services.impl;

import com.pitech.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    @Value("${image.folder}")
    String imageFolder;

    @Value("${files.path}")
    private String applicationFilesPath;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        File directory = new File(applicationFilesPath + imageFolder);
        File pathImage = new File(imageFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }

        return pathImage.getAbsoluteFile() + File.separator + file.getOriginalFilename();
    }

}
