package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Appointment;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleStatus;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.exception.UnAuthorizeException;
import com.bk.donglt.patient_manager.repository.AppointmentRepository;
import com.bk.donglt.patient_manager.service.FileUploadService;
import com.bk.donglt.patient_manager.service.UserService;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import com.bk.donglt.patient_manager.service.manager.DoctorService;
import com.bk.donglt.patient_manager.service.manager.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ScheduleStatusService scheduleStatusService;

    @Autowired
    private DoctorScheduleStatusService doctorScheduleStatusService;

    @Autowired
    private FileUploadService fileUploadService;

    //-----------------------------------------------find-------------------------------------------------------
    public Page<HospitalDto> findHospital(String name, Pageable pageable) {
        Page<HospitalDto> dtos = hospitalService.findSearchable(name, pageable)
                .map(HospitalDto::new);
        dtos.forEach(dto -> dto.setFiles(fileUploadService.getHospitalFiles(dto.getId())));
        return dtos;
    }

    public List<DepartmentDto> findDepartment(Long hospitalId) {
        Hospital hospital = hospitalService.findById(hospitalId);
        List<DepartmentDto> dtos = departmentService.findSearchable(hospitalId).stream()
                .map(department -> new DepartmentDto(hospital, department))
                .collect(Collectors.toList());
        dtos.forEach(dto -> dto.setFiles(fileUploadService.getDepartmentFiles(dto.getId())));
        return dtos;
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

        Schedule schedule = scheduleService.findById(scheduleId);
        ScheduleStatus scheduleStatus = scheduleStatusService.findByScheduleId(scheduleId);
        if (schedule.getLimit() == scheduleStatus.getCurrentBook())
            throw new BadRequestException("Schedule is full");
        scheduleStatusService.increase(scheduleStatus);
        appointment.setSchedule(schedule);

        /*if (doctorId != null) {
            if (!schedule.contain(doctorId))
                throw new BadRequestException("Doctor not working in this schedule");

            DoctorScheduleStatus doctorScheduleStatus = doctorScheduleStatusService.findByScheduleIdAndDoctorId(scheduleId, doctorId);
            if (schedule.getDoctorLimit() == doctorScheduleStatus.getCurrentBook())
                throw new BadRequestException("Doctor schedule is full");
            doctorScheduleStatusService.increase(doctorScheduleStatus);
            appointment.setDoctor(doctorService.findById(doctorId));
        }*/
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
