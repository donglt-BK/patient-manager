package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.DoctorScheduleStatus;
import com.bk.donglt.patient_manager.repository.DoctorScheduleStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleStatusService extends BaseService<DoctorScheduleStatus, DoctorScheduleStatusRepository> {
    public DoctorScheduleStatus findByScheduleIdAndDoctorId(Long scheduleId, Long doctorId) {
        return repository.findByScheduleIdAndDoctorId(scheduleId, doctorId);
    }

    public void create(Long scheduleId, List<Doctor> doctors) {
        save(doctors.stream()
                .map(doctor -> new DoctorScheduleStatus(scheduleId, doctor))
                .collect(Collectors.toList())
        );
    }


    public void create(Long scheduleId, Doctor doctor) {
        save(new DoctorScheduleStatus(scheduleId, doctor));
    }

    public void increase(DoctorScheduleStatus doctorScheduleStatus) {
        doctorScheduleStatus.increase();
        update(doctorScheduleStatus);
    }


    public void decrease(DoctorScheduleStatus doctorScheduleStatus) {
        doctorScheduleStatus.decrease();
        update(doctorScheduleStatus);
    }
}
