package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleChangeRequest;
import com.bk.donglt.patient_manager.enums.Shift;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.ScheduleRepository;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import com.bk.donglt.patient_manager.service.manager.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService extends BaseService<Schedule, ScheduleRepository> {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleChangeRequestService scheduleChangeRequestService;

    @Autowired
    private ScheduleStatusService scheduleStatusService;

    @Autowired
    private DoctorScheduleStatusService doctorScheduleStatusService;

    public List<Schedule> findSchedules(Long departmentId, Date start, Date end) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return repository.findByDateBetweenAndDepartmentIdAndIsDeletedFalse(start, end, departmentId);
    }
    public Schedule create(Date date, Shift shift, int scheduleLimit, int doctorLimit, Long departmentId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule = save(schedule);

            scheduleStatusService.create(schedule.getId());
        }
        schedule.setScheduleLimit(scheduleLimit);
        schedule.setDoctorLimit(doctorLimit);
        return update(schedule);
    }

    public Schedule assign(Date date, Shift shift, int scheduleLimit, int doctorLimit, Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule = save(schedule);

            scheduleStatusService.create(schedule.getId());
        }
        schedule.setScheduleLimit(scheduleLimit);
        schedule.setDoctorLimit(doctorLimit);
        schedule.addDoctor(doctor);
        doctorScheduleStatusService.create(schedule.getId(), doctor);

        return update(schedule);
    }

    void createSchedule(Date date, Shift shift, Doctor doctor, Department department) {
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getHospitalId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule.setDoctorLimit(30);
            schedule.setScheduleLimit(50);
            save(schedule);

            scheduleStatusService.create(schedule.getId());
        }
        doctorScheduleStatusService.create(schedule.getId(), doctor);
        schedule.addDoctor(doctor);
        update(schedule);
    }

    public Schedule deAssign(Date date, Shift shift, Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);

        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getId());
        if (schedule == null) {
            throw new BadRequestException("Schedule not exist");
        } else {
            schedule.removeDoctor(doctor);
            return update(schedule);
        }
    }

    public List<Schedule> findMySchedule(Long departmentId) {
        Doctor doctor = doctorService.findMeInDepartment(departmentId);
        return repository.findByUserId(doctor.getId());
    }

    public ScheduleChangeRequest requestChange(Date date, Shift shift, Long scheduleId) {
        Schedule schedule = findById(scheduleId);
        Doctor doctor = doctorService.findMeInDepartment(schedule.getDepartmentId());

        if (!schedule.contain(doctor)) {
            throw new BadRequestException("Doctor not assigned to this schedule");
        } else {
            return scheduleChangeRequestService.request(doctor, schedule, date, shift);
        }
    }
}
