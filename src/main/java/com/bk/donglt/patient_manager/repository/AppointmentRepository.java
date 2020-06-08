package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends BaseRepository<Appointment> {
    Page<Appointment> findByUser_IdAndIsDeletedFalse(Long id, Pageable pageable);
    Page<Appointment> findByDoctor_IdAndIsDeletedFalse(Long id, Pageable pageable);
    Page<Appointment> findBySchedule_DepartmentIdAndIsDeletedFalse(Long id, Pageable pageable);
    Appointment findByIdAndIsCanceledFalse(Long id);
}
