package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileApi {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        return fileUploadService.upload(request, file);
    }


    @GetMapping("/get/{fileUrl}")
    @ResponseBody
    public FileSystemResource upload(@PathVariable String fileUrl) {
        return new FileSystemResource(fileUploadService.load(fileUrl));
    }
}
