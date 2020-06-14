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

    private void addFile(String key, Long id, String file) {
        save(new FileUpload(key + id, file));
    }

    public void addDepartmentFile(Long departmentId, String file) {
        addFile("dept_", departmentId, file);
    }

    public void addHospitalFile(Long hospitalId, String file) {
        addFile("hos_", hospitalId, file);
    }

    private void deleteFiles(String key, Long id, String file) {
        FileUpload fileUpload = repository.findByFile(file);
        delete(fileUpload.getId());
    }

    public void deleteDepartmentFile(Long departmentId, String file) {
        deleteFiles("dept_", departmentId, file);
    }

    public void deleteHospitalFile(Long hospitalId, String file) {
        deleteFiles("hos_", hospitalId, file);
    }
}
