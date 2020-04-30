package com.bk.donglt.patient_manager.dto;

import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
    private Long id;
    private String name;
    private List<User> managers;

    private List<Long> addManagerIds;
    private List<Long> removeManagerIds;

    private List<Long> addDoctorIds;
    private List<Long> removeDoctorIds;

    private List<User> addManagers;
    private List<Doctor> addDoctors;

    public DepartmentDto(Department department) {
        id = department.getId();
        name = department.getName();
        managers = department.getManagers();
    }
}
