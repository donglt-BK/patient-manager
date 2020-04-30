package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Department, DepartmentRepository> {
    Page<Department> findAvailable(Long hospitalId, Pageable pageRequest) {
        return repository.findByHospitalIdAndIsActiveFalseAndIsDeletedFalse(hospitalId, pageRequest);
    }
}
