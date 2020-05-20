package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {
    Page<Doctor> findByDepartmentIdAndIsDeletedFalse(Long id, Pageable pageable);
    Page<Doctor> findByUser_NameContaining(String name, Pageable pageable);

    @Query("select d.id from Doctor d where d.user.id = ?1")
    List<Long> findDoctorIdsByUserId(Long id);
}
