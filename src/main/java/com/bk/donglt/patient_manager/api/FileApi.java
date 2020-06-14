package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.service.FileUploadService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileApi extends BaseResource<FileUploadService> {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file, HttpServletRequest request) {
        return service.upload(request, file);
    }

    @GetMapping("/get/{fileUrl}")
    @ResponseBody
    public FileSystemResource upload(@PathVariable String fileUrl) {
        return new FileSystemResource(service.load(fileUrl));
    }

    @GetMapping("/department")
    @ResponseBody
    public String getDepartmentFiles(@RequestParam("departmentId") Long departmentId) {
        return service.getDepartmentFiles(departmentId);
    }

    @GetMapping("/hospital")
    @ResponseBody
    public String getHospitalFiles(@RequestParam("hospitalId") Long hospitalId) {
        return service.getHospitalFiles(hospitalId);
    }

    @PostMapping("/department/add")
    public void addDepartmentFile(@RequestParam("file") String file,
                                  @RequestParam("departmentId") Long departmentId) {
        service.addDepartmentFile(departmentId, file);
    }

    @PostMapping("/hospital/add")
    public void addHospitalFile(@RequestParam("file") String file,
                                @RequestParam("hospitalId") Long hospitalId) {
        service.addHospitalFile(hospitalId, file);
    }

    @PostMapping("/department/delete")
    public void deleteDepartmentFile(@RequestParam("file") String file,
                                     @RequestParam("departmentId") Long departmentId) {
        service.deleteDepartmentFile(departmentId, file);
    }

    @PostMapping("/hospital/delete")
    public void deleteHospitalFile(@RequestParam("file") String file,
                                   @RequestParam("hospitalId") Long hospitalId) {
        service.deleteHospitalFile(hospitalId, file);
    }
}
