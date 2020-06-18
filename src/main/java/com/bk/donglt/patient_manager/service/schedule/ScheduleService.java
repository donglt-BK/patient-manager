package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleStatus;
import com.bk.donglt.patient_manager.enums.Shift;
import com.bk.donglt.patient_manager.repository.ScheduleRepository;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService extends BaseService<Schedule, ScheduleRepository> {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleStatusService scheduleStatusService;

    public List<Schedule> findSchedules(Long departmentId, Date start, Date end) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        List<Schedule> schedules = repository.findByDateBetweenAndDepartmentIdAndIsDeletedFalse(start, end, departmentId);
        Map<Long, ScheduleStatus> status = scheduleStatusService.getStatus(schedules.stream().map(Schedule::getId).collect(Collectors.toList()));
        schedules.forEach(schedule -> {
            ScheduleStatus scheduleStatus = status.get(schedule.getId());

            if (scheduleStatus != null) {
                schedule.setBookingStatus(scheduleStatus.getCurrentBook());
                schedule.setPos(scheduleStatus.getPos());
            }
        });
        return schedules;
    }

    public List<Schedule> findBookSchedules(Long departmentId, Date start, Date end) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
            Date today = null;
            try {
                today = dateFormat.parse(dateFormat.format(new Date()));
            } catch (ParseException ignore) {
            }
            calendar.setTime(today);
            calendar.add(Calendar.DATE, 1);
            Date t = calendar.getTime();
            if (start.before(calendar.getTime()))
                start = calendar.getTime();
        }
        List<Schedule> schedules = repository.findByDateBetweenAndDepartmentIdAndIsDeletedFalse(start, end, departmentId);
        schedules = schedules.stream().filter(s -> !s.isClosed()).collect(Collectors.toList());
        Map<Long, ScheduleStatus> status = scheduleStatusService.getStatus(schedules.stream().map(Schedule::getId).collect(Collectors.toList()));
        schedules.forEach(schedule -> {
            ScheduleStatus scheduleStatus = status.get(schedule.getId());

            if (scheduleStatus != null) {
                schedule.setBookingStatus(scheduleStatus.getCurrentBook());
                schedule.setPos(scheduleStatus.getPos());
            }
        });
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
            schedule.setLimit(100);
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
}
