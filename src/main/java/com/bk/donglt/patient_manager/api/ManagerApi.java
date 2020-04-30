package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.HospitalDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.service.manager.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage")
public class ManagerApi extends BaseResource<ManagerService> {
    @GetMapping("/hospital/list")
    public ResponseEntity<Page<Hospital>> getHospital(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getHospitals(getPageable(page, size)));
    }

    @PostMapping("/hospital/add")
    public ResponseEntity<Hospital> addHospital(@RequestBody HospitalDto hospital) {
        return ResponseEntity.ok().body(service.addHospital(hospital));
    }

    @PostMapping("/hospital/update")
    public ResponseEntity<Hospital> updateHospital(@RequestBody HospitalDto update) {
        return ResponseEntity.ok().body(service.updateHospital(update));
    }

    @PostMapping("/hospital/active")
    public ResponseEntity<Hospital> activeHospital(@RequestParam("id") long id, @RequestParam("isActive") boolean active) {
        service.activeHospital(id, active);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hospital/delete")
    public ResponseEntity<Hospital> deleteHospital(@RequestParam("id") long id) {
        service.deleteHospital(id);
        return ResponseEntity.ok().build();
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/department/list")
    public ResponseEntity<Page<Department>> getDepartment(
            @RequestParam("hospitalId") long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getDepartments(id, getPageable(page, size)));
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/doctor/list")
    public ResponseEntity<Page<Doctor>> getDoctor(
            @RequestParam("departmentId") long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getDoctors(id, getPageable(page, size)));
    }
}
