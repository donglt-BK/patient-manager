package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleChangeRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleChangeRequestRepository extends BaseRepository<ScheduleChangeRequest> {
    Page<ScheduleChangeRequest> findBySchedule_DepartmentId(Long departmentId, Pageable pageable);

    Page<ScheduleChangeRequest> findByDoctor_Id(Long doctorId, Pageable pageable);

}
