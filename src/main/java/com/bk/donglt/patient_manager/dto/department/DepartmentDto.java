package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Status;
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
    private Status status;

    public DepartmentDto(Hospital hospital, Department department) {
        id = department.getId();
        name = department.getName();
        hospitalId = hospital.getId();
        hospitalName = hospital.getName();
        managerCount = department.getManagers().size();
        status = department.getStatus();
    }
}
