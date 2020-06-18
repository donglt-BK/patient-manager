package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.service.FileUploadService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileApi extends BaseResource<FileUploadService> {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file) {
        return service.upload(file);
    }

    @GetMapping("/get/{fileUrl}")
    @ResponseBody
    public FileSystemResource upload(@PathVariable String fileUrl) {
        return new FileSystemResource(service.load(fileUrl));
    }

    @GetMapping("/hospital")
    @ResponseBody
    public String getHospitalFiles(@RequestParam("hospitalId") Long hospitalId) {
        return service.getHospitalFiles(hospitalId);
    }

    @PostMapping("/hospital/add")
    public void addHospitalFile(@RequestParam("file") String file,
                                @RequestParam("hospitalId") Long hospitalId) {
        service.addHospitalFile(hospitalId, file);
    }

    @PostMapping("/hospital/delete")
    public void deleteHospitalFile(@RequestParam("file") String file) {
        service.deleteHospitalFile(file);
    }
}
