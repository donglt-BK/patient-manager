package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleStatus;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface ScheduleStatusRepository extends BaseRepository<ScheduleStatus> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    ScheduleStatus findByScheduleId(Long id);

    List<ScheduleStatus> findByScheduleIdIn(List<Long> scheduleIds);
}
