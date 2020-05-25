package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDetailDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDetailDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.dto.manage.ManagerChangeDto;
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
    public ResponseEntity<Page<HospitalDto>> getHospital(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getHospitals(getPageable(page, size)));
    }

    @GetMapping("/hospital/detail")
    public ResponseEntity<HospitalDetailDto> addHospital(@RequestParam("id") long id) {
        return ResponseEntity.ok().body(service.getHospital(id));
    }

    @PostMapping("/hospital/add")
    public ResponseEntity<HospitalDetailDto> addHospital(@RequestBody HospitalDataDto hospital) {
        return ResponseEntity.ok().body(service.addHospital(hospital));
    }

    @PostMapping("/hospital/update")
    public ResponseEntity<HospitalDetailDto> updateHospital(@RequestBody HospitalDataDto update) {
        return ResponseEntity.ok().body(service.updateHospital(update));
    }

    @PostMapping("/hospital/changeManager")
    public ResponseEntity<HospitalDetailDto> updateHospitalManager(@RequestBody ManagerChangeDto update) {
        return ResponseEntity.ok().body(service.updateHospitalManager(update));
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
    public ResponseEntity<Page<DepartmentDto>> getDepartment(
            @RequestParam("hospitalId") long id,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getDepartments(id, getPageable(page, size)));
    }

    @GetMapping("/department/detail")
    public ResponseEntity<DepartmentDetailDto> addDepartment(
            @RequestParam("departmentId") long departmentId) {
        return ResponseEntity.ok().body(service.getDepartment(departmentId));
    }

    @PostMapping("/department/add")
    public ResponseEntity<DepartmentDetailDto> addDepartment(@RequestBody DepartmentDataDto department) {
        return ResponseEntity.ok().body(service.addDepartment(department));
    }

    @PostMapping("/department/update")
    public ResponseEntity<DepartmentDetailDto> updateDepartment(@RequestBody DepartmentDataDto update) {
        return ResponseEntity.ok().body(service.updateDepartment(update));
    }

    @PostMapping("/department/changeManager")
    public ResponseEntity<DepartmentDetailDto> updateDepartmentManager(@RequestBody ManagerChangeDto update) {
        return ResponseEntity.ok().body(service.updateDepartmentManager(update));
    }

    @PostMapping("/department/active")
    public ResponseEntity<Department> activeDepartment(
            @RequestParam("departmentId") long departmentId,
            @RequestParam("isActive") boolean active) {
        service.activeDepartment(departmentId, active);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/department/delete")
    public ResponseEntity<Department> deleteDepartment(
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
            @RequestParam("doctorId") long doctorId) {
        return ResponseEntity.ok().body(service.getDoctor(doctorId));
    }

    @PostMapping("/doctor/add")
    public ResponseEntity<DoctorDto> addDoctor(@RequestBody DoctorDataDto doctor) {
        return ResponseEntity.ok().body(service.addDoctor(doctor));
    }

    @PostMapping("/doctor/updateLicense")
    public ResponseEntity<DoctorDto> updateDoctorLicense(@RequestBody DoctorDataDto update) {
        return ResponseEntity.ok().body(service.updateLicense(update));
    }

    @PostMapping("/doctor/active")
    public ResponseEntity<Doctor> activeDoctor(
            @RequestParam("doctorId") long doctorId,
            @RequestParam("isActive") boolean active) {
        service.activeDoctor(doctorId, active);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/doctor/delete")
    public ResponseEntity<Doctor> deleteDoctor(
            @RequestParam("departmentId") long departmentId,
            @RequestParam("doctorId") long doctorId) {
        service.deleteDoctor(departmentId, doctorId);
        return ResponseEntity.ok().build();
    }
}
