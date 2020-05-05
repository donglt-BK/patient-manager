package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class DepartmentDetailDto {
    private Long id;
    private String name;
    private Long hospitalId;
    private String hospitalName;
    private List<User> managers;

    public DepartmentDetailDto(Department department) {
        id = department.getId();
        name = department.getName();
        hospitalId = department.getHospitalId();
        if (department.getManagers() != null) {
            managers = new ArrayList<>(department.getManagers());
        } else {
            managers = new LinkedList<>();
        }
    }
}