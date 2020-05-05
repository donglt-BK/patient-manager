package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.entity.hospital.Department;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
    private Long id;
    private String name;
    private Long hospitalId;
    private String hospitalName;
    private int managerCount;

    public DepartmentDto(Department department) {
        id = department.getId();
        name = department.getName();
        hospitalId = department.getHospitalId();
        managerCount = department.getManagers().size();
    }
}
