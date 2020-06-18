package com.bk.donglt.patient_manager.service;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.FileUpload;
import com.bk.donglt.patient_manager.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileUploadService extends BaseService<FileUpload, FileUploadRepository> {
    @Value("${app.file-upload.folder}")
    private String folder;

    public String upload(MultipartFile file) {
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

    public void addHospitalFile(Long id, String file) {
        save(new FileUpload("hos_" + id, file));
    }

    public String getHospitalFiles(Long hospitalId) {
        return repository.findByOwnerAndIsDeletedFalse("hos_" + hospitalId)
                .stream().map(FileUpload::getFile)
                .collect(Collectors.joining("||"));
    }

    public void deleteHospitalFile(String file) {
        FileUpload fileUpload = repository.findByFile(file);
        if (fileUpload.getOwner().startsWith("hos_")) {
            delete(fileUpload.getId());
        }
    }
}
