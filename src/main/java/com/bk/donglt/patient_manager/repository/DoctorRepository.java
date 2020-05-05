package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {
    Page<Doctor> findByDepartmentIdAndIsDeletedFalse(Long id, Pageable pageable);
    Page<Doctor> findByUser_NameContaining(String name, Pageable pageable);
}
