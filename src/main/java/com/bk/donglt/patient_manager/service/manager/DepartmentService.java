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
        return repository.findByHospitalIdAndIsDeletedFalse(hospitalId, pageRequest);
    }
/*
    public Department addDepartment(DepartmentDto newData) {
        Department department = new Department();
        department.update(newData);

        return save(department);
    }

    Department updateDepartment(DepartmentDto updateData) {
        Department department = findById(updateData.getId());
        processData(updateData);
        department.update(updateData);
        return update(department);
    }

    public void activeDepartment(long departmentId, boolean active) {
        Department department = findById(departmentId);
        department.setIsActive(active);

        update(department);
    }

    public void deleteDepartment(long departmentId) {
        Department department = findById(departmentId);
        department.setDeleted(true);

        update(department);
    }
*/
}
