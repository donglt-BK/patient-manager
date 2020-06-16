package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {
    Page<Doctor> findByDepartmentIdAndUser_NameContainingAndStatusNotAndIsDeletedFalse(Long departmentId, String name, Status status, Pageable pageable);
    Page<Doctor> findByDepartmentIdAndIsDeletedFalse(Long departmentId, Pageable pageable);
    List<Doctor> findByDepartmentIdAndIsDeletedFalse(Long departmentId);

    @Query("select d.id from Doctor d where d.departmentId = ?1 and d.isDeleted = false ")
    List<Long> findDoctorIds(Long departmentId);

    @Query("select d.id from Doctor d where d.user.id = ?1")
    List<Long> findDoctorIdsByUserId(Long userId);

    Doctor findByUser_IdAndDepartmentIdAndIsDeletedFalse(Long userId, Long departmentId);
}
