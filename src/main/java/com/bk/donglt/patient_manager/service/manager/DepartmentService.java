package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.manage.ManagerChangeDto;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.enums.Status;
import com.bk.donglt.patient_manager.repository.DepartmentRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService extends BaseService<Department, DepartmentRepository> {
    @Autowired
    private UserService userService;

    public Page<Department> findSearchable(Long hospitalId, String name, Pageable pageRequest) {
        return repository.findByHospitalIdAndNameContainingAndStatusNotAndIsDeletedFalse(hospitalId, name, Status.HIDDEN, pageRequest);
    }

    Page<Department> findAll(Long hospitalId, Pageable pageRequest) {
        return repository.findByHospitalIdAndIsDeletedFalse(hospitalId, pageRequest);
    }

    private void processData(ManagerChangeDto data) {
        if (data.getAddedManagerIds() != null)
            data.setAddedManagers(userService.findUsers(data.getAddedManagerIds()));
    }

    Department addDepartment(DepartmentDataDto newData) {
        Department department = new Department();
        department.update(newData);

        return save(department);
    }

    Department updateDepartment(Department department, DepartmentDataDto updateData) {
        department.update(updateData);
        return update(department);
    }

    Department updateDepartment(Department department, ManagerChangeDto updateData) {
        processData(updateData);
        department.update(updateData);
        return update(department);
    }
}
