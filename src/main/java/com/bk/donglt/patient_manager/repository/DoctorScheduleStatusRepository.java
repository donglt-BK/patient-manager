package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.DoctorScheduleStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface DoctorScheduleStatusRepository extends BaseRepository<DoctorScheduleStatus> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    DoctorScheduleStatus findByScheduleIdAndDoctorId(Long scheduleId, Long doctorId);
}
