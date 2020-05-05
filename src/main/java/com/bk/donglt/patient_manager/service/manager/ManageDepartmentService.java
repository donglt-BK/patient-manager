package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.DepartmentRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManageDepartmentService extends BaseService<Department, DepartmentRepository> {
    @Autowired
    private UserService userService;

    Page<Department> findAvailable(Long hospitalId, Pageable pageRequest) {
        return repository.findByHospitalIdAndIsDeletedFalse(hospitalId, pageRequest);
    }

    private void processData(DepartmentDataDto data) {
        if (data.getAddedManagerIds() != null)
            data.setAddedManagers(userService.findUsers(data.getAddedManagerIds()));
    }

    Department addDepartment(DepartmentDataDto newData) {
        processData(newData);
        Department department = new Department();
        department.update(newData);

        return save(department);
    }

    Department updateDepartment(DepartmentDataDto updateData) {
        Department department = findById(updateData.getId());
        if (!department.getHospitalId().equals(updateData.getHospitalId()))
            throw new BadRequestException("Department not in this hospital");

        processData(updateData);
        department.update(updateData);
        return update(department);
    }

    void activeDepartment(long hospitalId, long departmentId, boolean active) {
        Department department = findById(departmentId);
        if (!department.getHospitalId().equals(hospitalId))
            throw new BadRequestException("Department not in this hospital");

        department.setIsActive(active);
        update(department);
    }
}
