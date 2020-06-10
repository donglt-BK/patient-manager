package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Appointment;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.exception.UnAuthorizeException;
import com.bk.donglt.patient_manager.repository.AppointmentRepository;
import com.bk.donglt.patient_manager.service.UserService;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import com.bk.donglt.patient_manager.service.manager.DoctorService;
import com.bk.donglt.patient_manager.service.manager.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppointmentService extends BaseService<Appointment, AppointmentRepository> {
    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    //-----------------------------------------------find-------------------------------------------------------
    public Page<HospitalDto> findHospital(String name, Pageable pageable) {
        return hospitalService.findSearchable(name, pageable).map(HospitalDto::new);
    }

    public Page<DepartmentDto> findDepartment(Long hospitalId, String name, Pageable pageable) {
        Hospital hospital = hospitalService.findById(hospitalId);
        return departmentService.findSearchable(hospitalId, name, pageable).map(department -> new DepartmentDto(hospital, department));
    }

    public Page<DoctorDto> findDoctor(Long departmentId, String name, Pageable pageable) {
        Department department = departmentService.findById(departmentId);
        return doctorService.findSearchable(departmentId, name, pageable).map(doctor -> new DoctorDto(department, doctor));
    }

    //-----------------------------------------------book-------------------------------------------------------
    public Page<Appointment> listMyAppointment(Pageable pageable) {
        long userId = getCurrentUser().getUser().getId();
        return repository.findByUser_IdAndIsDeletedFalse(userId, pageable);
    }

    public Page<Appointment> listDoctorAppointment(Long departmentId, Pageable pageable) {
        Doctor doctor = doctorService.findMeInDepartment(departmentId);
        return repository.findByDoctor_IdAndIsDeletedFalse(doctor.getId(), pageable);
    }

    public Page<Appointment> listDepartmentAppointment(Long departmentId, Pageable pageable) {
        checkUserAuthorize(departmentService.findById(departmentId));
        return repository.findBySchedule_DepartmentIdAndIsDeletedFalse(departmentId, pageable);
    }

    public Appointment book(long scheduleId, Long doctorId) {
        Appointment appointment = new Appointment();
        appointment.setUser(userService.findById(getCurrentUser().getUser().getId()));
        appointment.setSchedule(scheduleService.findById(scheduleId));
        if (doctorId != null) {
            appointment.setDoctor(doctorService.findById(doctorId));
        }
        return save(appointment);
    }

    public Appointment receive(long appointmentId) {
        Appointment appointment = findById(appointmentId);
        Doctor doctor = doctorService.findMeInDepartment(appointment.getSchedule().getDepartmentId());
        if (!doctor.getId().equals(appointment.getDoctor().getId()))
            throw new UnAuthorizeException();
        appointment.setReceived(true);
        return update(appointment);
    }

    public Appointment cancel(long appointmentId) {
        Appointment appointment = findById(appointmentId);
        if (appointment.isReceived() || appointment.getSchedule().getDate().before(new Date()))
            throw new BadRequestException("Appointment can NOT be canceled");
        if (!appointment.getUser().getId().equals(getCurrentUser().getUser().getId()))
            throw new UnAuthorizeException();
        appointment.setCanceled(true);
        return update(appointment);
    }
}
