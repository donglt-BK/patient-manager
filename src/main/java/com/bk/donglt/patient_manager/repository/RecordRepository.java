package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.record.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends BaseRepository<Record> {
    Page<Record> findByPatient_IdAndIsDeletedFalse(Long id, Pageable pageable);
}
