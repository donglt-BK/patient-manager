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

import java.util.List;

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
    public ResponseEntity<List<DepartmentDto>> findDepartment(
            @RequestParam(name = "hospitalId") Long hospitalId) {
        return ResponseEntity.ok().body(service.findDepartment(hospitalId));
    }

    @GetMapping("/find/doctor")
    public ResponseEntity<Page<DoctorDto>> findDoctor(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findDoctor(departmentId, name, getPageable(page, size)));
    }

    @GetMapping("/myAppointment")
    public ResponseEntity<Page<Appointment>> myAppointment(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.listMyAppointment(getPageable(page, size)));
    }

    @GetMapping("/doctorAppointment")
    public ResponseEntity<Page<Appointment>> doctorAppointment(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.listDoctorAppointment(departmentId, getPageable(page, size)));
    }

    @GetMapping("/departmentAppointment")
    public ResponseEntity<Page<Appointment>> departmentAppointment(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.listDepartmentAppointment(departmentId, getPageable(page, size)));
    }

    @PostMapping("/book")
    public ResponseEntity<Appointment> booking(
            @RequestParam("scheduleId") long scheduleId,
            @RequestParam(name = "doctorId", required = false) Long doctorId) {
        return ResponseEntity.ok().body(
                service.book(scheduleId, doctorId)
        );
    }

    @PostMapping("/receive")
    public ResponseEntity<Appointment> receive(@RequestParam(name = "appointmentId") Long appointmentId) {
        return ResponseEntity.ok().body(
                service.receive(appointmentId)
        );
    }


    @PostMapping("/cancel")
    public ResponseEntity<Appointment> cancel(@RequestParam(name = "appointmentId") Long appointmentId) {
        return ResponseEntity.ok().body(
                service.cancel(appointmentId)
        );
    }
}
