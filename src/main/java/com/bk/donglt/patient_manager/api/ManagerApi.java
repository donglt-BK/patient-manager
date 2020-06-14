package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.dto.manage.ManagerChangeDto;
import com.bk.donglt.patient_manager.service.manager.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage")
public class ManagerApi extends BaseResource<ManagerService> {
    @GetMapping("/hospital/list")
    public ResponseEntity<Page<HospitalDto>> getHospital(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getHospitals(getPageable(page, size)));
    }

    @GetMapping("/hospital/detail")
    public ResponseEntity<HospitalDto> detailHospital(@RequestParam("id") long id) {
        return ResponseEntity.ok().body(service.getHospital(id));
    }

    @PostMapping("/hospital/add")
    public ResponseEntity<HospitalDto> addHospital(@RequestBody HospitalDataDto hospital) {
        return ResponseEntity.ok().body(service.addHospital(hospital));
    }

    @PostMapping("/hospital/update")
    public ResponseEntity<HospitalDto> updateHospital(@RequestBody HospitalDataDto update) {
        return ResponseEntity.ok().body(service.updateHospital(update));
    }

    @PostMapping("/hospital/changeManager")
    public ResponseEntity<HospitalDto> updateHospitalManager(@RequestBody ManagerChangeDto update) {
        return ResponseEntity.ok().body(service.updateHospitalManager(update));
    }

    @PostMapping("/hospital/delete")
    public ResponseEntity<Void> deleteHospital(@RequestParam("id") long id) {
        service.deleteHospital(id);
        return ResponseEntity.ok().build();
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/department/list")
    public ResponseEntity<Page<DepartmentDto>> getDepartment(
            @RequestParam("hospitalId") long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getDepartments(id, getPageable(page, size)));
    }

    @GetMapping("/department/detail")
    public ResponseEntity<DepartmentDto> addDepartment(
            @RequestParam("id") long departmentId) {
        return ResponseEntity.ok().body(service.getDepartment(departmentId));
    }

    @PostMapping("/department/add")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDataDto department) {
        return ResponseEntity.ok().body(service.addDepartment(department));
    }

    @PostMapping("/department/update")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDataDto update) {
        return ResponseEntity.ok().body(service.updateDepartment(update));
    }

    @PostMapping("/department/changeManager")
    public ResponseEntity<DepartmentDto> updateDepartmentManager(@RequestBody ManagerChangeDto update) {
        return ResponseEntity.ok().body(service.updateDepartmentManager(update));
    }

    @PostMapping("/department/delete")
    public ResponseEntity<Void> deleteDepartment(
            @RequestParam("hospitalId") long hospitalId,
            @RequestParam("departmentId") long departmentId) {
        service.deleteDepartment(hospitalId, departmentId);
        return ResponseEntity.ok().build();
    }
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/doctor/list")
    public ResponseEntity<Page<DoctorDto>> getDoctor(
            @RequestParam("departmentId") long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getDoctors(id, getPageable(page, size)));
    }

    @GetMapping("/doctor/detail")
    public ResponseEntity<DoctorDto> addDoctor(
            @RequestParam("id") long doctorId) {
        return ResponseEntity.ok().body(service.getDoctor(doctorId));
    }

    @PostMapping("/doctor/add")
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDataDto doctor) {
        return ResponseEntity.ok().body(service.addDoctor(doctor));
    }

    @PostMapping("/doctor/update")
    public ResponseEntity<DoctorDto> updateDoctor(@RequestBody DoctorDataDto update) {
        return ResponseEntity.ok().body(service.updateDoctor(update));
    }

    @PostMapping("/doctor/delete")
    public ResponseEntity<Void> deleteDoctor(
            @RequestParam("departmentId") long departmentId,
            @RequestParam("doctorId") long doctorId) {
        service.deleteDoctor(departmentId, doctorId);
        return ResponseEntity.ok().build();
    }
}
