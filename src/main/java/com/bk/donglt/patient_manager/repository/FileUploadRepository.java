package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.FileUpload;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploadRepository extends BaseRepository<FileUpload> {
    List<FileUpload> findByOwnerAndIsDeletedFalse(String owner);
}
