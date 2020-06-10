package com.bk.donglt.patient_manager.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {
    @Value("${app.file-upload.folder}")
    private String folder;

    public String upload(HttpServletRequest request, MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + getFileTail(file);
        String path = folder + fileName;
        File localFile = new File(path);
        try {
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private String getFileTail(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return "";
        String[] temp = originalFilename.split("\\.");
        return "." + temp[temp.length - 1];
    }

    public File load(String url) {
        return new File(folder + url);
    }
}
