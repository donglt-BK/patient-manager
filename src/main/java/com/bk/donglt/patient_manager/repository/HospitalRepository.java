package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends BaseRepository<Hospital> {
    Page<Hospital> findByNameContainingAndStatusNotAndIsDeletedFalse(String name, Status status, Pageable pageable);

    @Query(value = "select hospital_id from hospital_manager where manager_id = ?1", nativeQuery = true)
    List<Long> findManageHospitalIdsByUser(Long userId);
}
