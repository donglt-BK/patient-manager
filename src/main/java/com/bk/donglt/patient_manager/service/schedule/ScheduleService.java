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
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Schedule> findSchedules(Long departmentId, Date start, Date end) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        List<Schedule> schedules = repository.findByDateBetweenAndDepartmentIdAndIsDeletedFalse(start, end, departmentId);
        Map<Long, Integer> status = scheduleStatusService.getStatus(schedules.stream().map(Schedule::getId).collect(Collectors.toList()));
        schedules.forEach(schedule -> schedule.setBookingStatus(status.getOrDefault(schedule.getId(), 0)));
        return schedules;
    }

    public void toggle(Date date, Shift shift, boolean isClosed, Long departmentId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule.setClosed(isClosed);
            schedule = save(schedule);

            scheduleStatusService.create(schedule.getId());
        } else {
            schedule.setClosed(isClosed);
            update(schedule);
        }
    }

    public Schedule create(Date date, Shift shift, int limit, boolean isClosed, Long departmentId) {
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
        schedule.setLimit(limit);
        schedule.setClosed(isClosed);
        return update(schedule);
    }

    public Schedule assign(Date date, Shift shift, int limit, boolean isClosed, Long doctorId) {
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
        schedule.setLimit(limit);
        schedule.setClosed(isClosed);
        schedule.addDoctor(doctor);

        return update(schedule);
    }

    void createSchedule(Date date, Shift shift, Doctor doctor, Department department) {
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getHospitalId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule.setLimit(100);
            schedule.setClosed(false);
            save(schedule);

            scheduleStatusService.create(schedule.getId());
        }
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
