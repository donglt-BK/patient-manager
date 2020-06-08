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
public class DepartmentDetailDto {
    private Long id;
    private String name;
    private Long hospitalId;
    private String hospitalName;
    private Status status;
    private List<User> managers;

    public DepartmentDetailDto(Hospital hospital, Department department) {
        id = department.getId();
        name = department.getName();
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
