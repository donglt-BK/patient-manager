package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleChangeRequest;
import com.bk.donglt.patient_manager.enums.Shift;
import com.bk.donglt.patient_manager.repository.ScheduleChangeRequestRepository;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import com.bk.donglt.patient_manager.service.manager.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScheduleChangeRequestService extends BaseService<ScheduleChangeRequest, ScheduleChangeRequestRepository> {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ScheduleService scheduleService;

    public ScheduleChangeRequest request(Doctor doctor, Schedule schedule, Date date, Shift shift) {
        ScheduleChangeRequest changeRequest = new ScheduleChangeRequest();
        changeRequest.setDoctor(doctor);
        changeRequest.setSchedule(schedule);
        changeRequest.setDate(date);
        changeRequest.setShift(shift);
        return save(changeRequest);
    }

    public Page<ScheduleChangeRequest> findByDepartment(Long departmentId, Pageable pageable) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return repository.findBySchedule_DepartmentId(departmentId, pageable);
    }


    public Page<ScheduleChangeRequest> findMyChangeRequest(Long departmentId, Pageable pageable) {
        Doctor doctor = doctorService.findMeInDepartment(departmentId);
        return repository.findByDoctor_Id(doctor.getId(), pageable);
    }

    public void resolve(Long changeRequestId, boolean approve) {
        ScheduleChangeRequest changeRequest = findById(changeRequestId);
        Schedule schedule = changeRequest.getSchedule();
        Department department = departmentService.findById(schedule.getDepartmentId());
        checkUserAuthorize(department);
        Doctor doctor = doctorService.findMeInDepartment(schedule.getDepartmentId());
        if (approve) {
            schedule.removeDoctor(doctor);
            scheduleService.save(schedule);

            scheduleService.createSchedule(
                    changeRequest.getDate(),
                    changeRequest.getShift(),
                    changeRequest.getDoctor(),
                    department
            );
        }
        changeRequest.setApproved(approve);
        changeRequest.setResolved(true);
        save(changeRequest);
    }
}
