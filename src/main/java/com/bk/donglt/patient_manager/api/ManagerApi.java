package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.service.manager.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManagerApi extends BaseResource<ManagerService> {
    @GetMapping("/hospital/listIn")
    public ResponseEntity<List<HospitalDto>> getHospitalByIds() {
        return ResponseEntity.ok().body(service.findHospitalIdIn());
    }

    @GetMapping("/department/listIn")
    public ResponseEntity<List<DepartmentDto>> getDepartmentByIds(
            @RequestParam(name = "hospitalId") Long hospitalId) {
        return ResponseEntity.ok().body(service.findDepartmentIdIn(hospitalId));
    }

    @GetMapping("/doctor/findAll")
    public ResponseEntity<List<Doctor>> findAllDoctor(
            @RequestParam(name = "departmentId") Long departmentId) {
        return ResponseEntity.ok().body(service.findAllDoctor(departmentId));
    }
    
    @GetMapping("/hospital/list")
    public ResponseEntity<Page<HospitalDto>> getHospital(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getHospitals(getPageable(page, size)));
    }

    @PostMapping("/hospital/add")
    public ResponseEntity<HospitalDto> addHospital(@RequestBody HospitalDataDto hospital) {
        return ResponseEntity.ok().body(service.addHospital(hospital));
    }

    @PostMapping("/hospital/update")
    public ResponseEntity<HospitalDto> updateHospital(@RequestBody HospitalDataDto update) {
        return ResponseEntity.ok().body(service.updateHospital(update));
    }

    @PostMapping("/hospital/addManager")
    public ResponseEntity<User> addHospitalManager(
            @RequestParam("hospitalId") Long hospitalId,
            @RequestParam("managerId") Long managerId) {
        return ResponseEntity.ok().body(service.changeHospitalManager(hospitalId, managerId, true));
    }

    @PostMapping("/hospital/removeManager")
    public ResponseEntity<User> removeHospitalManager(
            @RequestParam("hospitalId") Long hospitalId,
            @RequestParam("managerId") Long managerId) {
        return ResponseEntity.ok().body(service.changeHospitalManager(hospitalId, managerId, false));
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

    @PostMapping("/department/add")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDataDto department) {
        return ResponseEntity.ok().body(service.addDepartment(department));
    }

    @PostMapping("/department/update")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDataDto update) {
        return ResponseEntity.ok().body(service.updateDepartment(update));
    }

    @PostMapping("/department/addManager")
    public ResponseEntity<User> addDepartmentManager(
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("managerId") Long managerId) {
        return ResponseEntity.ok().body(service.changeDepartmentManager(departmentId, managerId, true));
    }

    @PostMapping("/department/removeManager")
    public ResponseEntity<User> removeDepartmentManager(
            @RequestParam("departmentId") Long departmentId,
            @RequestParam("managerId") Long managerId) {
        return ResponseEntity.ok().body(service.changeDepartmentManager(departmentId, managerId, false));
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
    @GetMapping("/doctor/getAllDoctorId")
    public ResponseEntity<List<Long>> getAllDoctorId(@RequestParam("departmentId") long id) {
        return ResponseEntity.ok().body(service.getAllDoctorId(id));
    }
    @PostMapping("/doctor/add")
    public ResponseEntity<DoctorDto> addDoctor(
            @RequestParam("departmentId") long departmentId,
            @RequestParam("userId") long userId
            ) {
        return ResponseEntity.ok().body(service.addDoctor(departmentId, userId));
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
