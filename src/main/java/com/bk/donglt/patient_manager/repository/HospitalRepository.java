package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitalRepository extends BaseRepository<Hospital> {
    Page<Hospital> findByIsActiveFalseAndIsDeletedFalse(Pageable pageable);

}
