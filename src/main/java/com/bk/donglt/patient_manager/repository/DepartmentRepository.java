package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends BaseRepository<Department> {
    Page<Department> findByHospitalIdAndIsDeletedFalse(Long id, Pageable pageable);

    @Query(value = "select department_id from department_manager where manager_id = ?1", nativeQuery = true)
    List<Long> findManageDepartmentIdsByUser(Long userId);
}
