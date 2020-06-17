package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.enums.Status;
import com.bk.donglt.patient_manager.repository.DepartmentRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService extends BaseService<Department, DepartmentRepository> {
    @Autowired
    private UserService userService;

    public List<Department> findSearchable(Long hospitalId) {
        return repository.findByHospitalIdAndStatusNotAndIsDeletedFalseOrderByStatus(hospitalId, Status.HIDDEN);
    }

    Page<Department> findAll(Long hospitalId, Pageable pageRequest) {
        return repository.findByHospitalIdAndIsDeletedFalse(hospitalId, pageRequest);
    }

    List<Department> findAll(Long hospitalId) {
        return repository.findByHospitalIdAndIsDeletedFalse(hospitalId);
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

    User updateManager(Long departmentId, Long managerId, boolean isAdd) {
        Department department = findById(departmentId);
        User user = userService.findById(managerId);
        if (isAdd) department.addManager(user);
        else department.removeManager(user.getId());
        update(department);
        return user;
    }
}
