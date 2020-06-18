package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.Appointment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends BaseRepository<Appointment> {
    List<Appointment> findByUser_IdAndIsDeletedFalse(Long id);
}
