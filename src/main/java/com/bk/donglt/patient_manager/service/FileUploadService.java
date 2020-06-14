package com.bk.donglt.patient_manager.service;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.FileUpload;
import com.bk.donglt.patient_manager.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileUploadService extends BaseService<FileUpload, FileUploadRepository> {
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

    private List<FileUpload> getFiles(String key, Long id) {
        return repository.findByOwnerAndIsDeletedFalse(key + id);
    }

    public String getDepartmentFiles(Long departmentId) {
        List<FileUpload> files = getFiles("dept_", departmentId);
        return files
                .stream().map(FileUpload::getFile)
                .collect(Collectors.joining("||"));
    }

    public String getHospitalFiles(Long hospitalId) {
        return getFiles("hos_", hospitalId)
                .stream().map(FileUpload::getFile)
                .collect(Collectors.joining("||"));
    }

    private void saveFiles(String key, Long id, String files) {
        List<FileUpload> currentFiles = getFiles(key, id);
        String[] split = files.split("\\|\\|");
        List<String> saveFiles = Arrays.asList(split);

        List<Long> deletedFiles = new LinkedList<>();
        for (FileUpload currentFile : currentFiles) {
            if (saveFiles.contains(currentFile.getFile())) {
                saveFiles.remove(currentFile.getFile());
            } else {
                deletedFiles.add(currentFile.getId());
            }
        }

        save(saveFiles.stream()
                .map(file -> new FileUpload(key + id, file))
                .collect(Collectors.toList())
        );
        delete(deletedFiles);
    }

    public void saveDepartmentFiles(Long departmentId, String files) {
        saveFiles("dept_", departmentId, files);
    }

    public void saveHospitalFiles(Long hospitalId, String files) {
        saveFiles("hos_", hospitalId, files);
    }
}
