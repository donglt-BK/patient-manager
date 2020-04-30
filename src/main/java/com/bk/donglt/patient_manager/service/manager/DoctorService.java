package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends BaseService<Doctor, DoctorRepository> {
    Page<Doctor> findAvailable(Long departmentId, Pageable pageRequest) {
        return repository.findByDepartmentIdAndIsActiveFalseAndIsDeletedFalse(departmentId, pageRequest);
    }
}
