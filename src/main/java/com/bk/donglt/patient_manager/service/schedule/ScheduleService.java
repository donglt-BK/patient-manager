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

    public List<Schedule> findSchedules(Long departmentId, Date start, Date end) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return repository.findByDateBetweenAndDepartmentIdAndIsDeletedFalse(start, end, departmentId);
    }

    public Schedule assign(Date date, Shift shift, Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);
        return createSchedule(date, shift, doctor, department);
    }

    Schedule createSchedule(Date date, Shift shift, Doctor doctor, Department department) {
        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getHospitalId());
        if (schedule == null) {
            schedule = new Schedule();
            schedule.setDate(date);
            schedule.setShift(shift);
            schedule.setDepartmentId(department.getId());
            schedule.addDoctor(doctor);
            return save(schedule);
        } else {
            schedule.addDoctor(doctor);
            return update(schedule);
        }
    }

    public Schedule deAssign(Date date, Shift shift, Long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);

        Schedule schedule = repository.findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(date, shift, department.getHospitalId());
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
