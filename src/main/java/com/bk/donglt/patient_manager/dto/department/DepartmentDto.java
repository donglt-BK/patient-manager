package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class DepartmentDto {
    private Long id;
    private String name;
    private String description;
    private Long hospitalId;
    private String hospitalName;
    private Status status;
    private List<User> managers;

    private String files;

    public DepartmentDto(Hospital hospital, Department department) {
        id = department.getId();
        name = department.getName();
        description = department.getDescription();
        hospitalId = hospital.getId();
        hospitalName = hospital.getName();
        status = department.getStatus();

        if (department.getManagers() != null) {
            managers = new ArrayList<>(department.getManagers());
        } else {
            managers = new LinkedList<>();
        }
    }
}
