package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Appointment;
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
import com.bk.donglt.patient_manager.service.manager.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private ScheduleStatusService scheduleStatusService;

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
        return departmentService.findSearchable(hospitalId).stream()
                .map(department -> new DepartmentDto(hospital, department))
                .collect(Collectors.toList());
    }

    //-----------------------------------------------book-------------------------------------------------------
    public List<Appointment> listMyAppointment() {
        long userId = getCurrentUser().getUser().getId();
        List<Appointment> appointments = repository.findByUser_IdAndIsDeletedFalse(userId);
        List<Department> departments = departmentService.findByIdIn(
                appointments.stream()
                        .map(a -> a.getSchedule().getDepartmentId())
                        .collect(Collectors.toList())
        );
        Map<Long, String> hospitalName = hospitalService.findByIdIn(
                departments.stream()
                        .map(Department::getHospitalId)
                        .distinct().collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Hospital::getId, Hospital::getName));

        Map<Long, String> names = departments.stream().collect(Collectors.toMap(
                Department::getId,
                d -> d.getName() + "||" + hospitalName.get(d.getHospitalId())
        ));
        for (Appointment appointment : appointments) {
            String name = names.get(appointment.getSchedule().getDepartmentId());
            appointment.setDepartmentName(name.split("\\|\\|")[0]);
            appointment.setHospitalName(name.split("\\|\\|")[1]);
        }
        return appointments;
    }

    @Transactional
    public Appointment book(long scheduleId) {
        Appointment appointment = new Appointment();
        appointment.setUser(userService.findById(getCurrentUser().getUser().getId()));

        Schedule schedule = scheduleService.findById(scheduleId);
        ScheduleStatus scheduleStatus = scheduleStatusService.findByScheduleId(scheduleId);
        if (schedule.getLimit() == scheduleStatus.getCurrentBook())
            throw new BadRequestException("Schedule is full");
        scheduleStatusService.increase(scheduleStatus);
        appointment.setSchedule(schedule);
        appointment.setPos(scheduleStatus.getPos());

        return save(appointment);
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
