package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends BaseRepository<Department> {
    Page<Department> findByHospitalIdAndIsDeletedFalse(Long id, Pageable pageable);
    List<Department> findByHospitalIdAndIsDeletedFalse(Long id);

    Page<Department> findByHospitalIdAndNameContainingAndStatusNotAndIsDeletedFalse(Long id, String name, Status status, Pageable pageable);

    @Query(value = "select department_id from department_manager where manager_id = ?1", nativeQuery = true)
    List<Long> findManageDepartmentIdsByUser(Long userId);


    @Query(value = "select hospital_id from department where id in (select department_id from department_manager where manager_id = ?1)", nativeQuery = true)
    List<Long> findManageDepartmentHospitalIdsByUser(Long userId);
}
