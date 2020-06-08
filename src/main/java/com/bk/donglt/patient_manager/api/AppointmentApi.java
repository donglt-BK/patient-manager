package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Appointment;
import com.bk.donglt.patient_manager.service.schedule.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentApi extends BaseResource<AppointmentService> {
    @GetMapping("/find/hospital")
    public ResponseEntity<Page<HospitalDto>> findHospital(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findHospital(name, getPageable(page, size)));
    }

    @GetMapping("/find/department")
    public ResponseEntity<Page<DepartmentDto>> findDepartment(
            @RequestParam(name = "hospitalId") Long hospitalId,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findDepartment(hospitalId, name, getPageable(page, size)));
    }

    @GetMapping("/find/doctor")
    public ResponseEntity<Page<DoctorDto>> findDoctor(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findDoctor(departmentId, name, getPageable(page, size)));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Appointment>> list(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.listMyAppointment(getPageable(page, size)));
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> booking(
            @RequestParam("scheduleId") long scheduleId,
            @RequestParam(name = "doctorId", required = false) Long doctorId) {
        return ResponseEntity.ok().body(
                service.book(scheduleId, doctorId)
        );
    }
}
