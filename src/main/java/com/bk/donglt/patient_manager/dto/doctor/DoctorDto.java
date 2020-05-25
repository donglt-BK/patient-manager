package com.bk.donglt.patient_manager.dto.doctor;

import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {
    private long id;
    private String name;
    private String licenceUrl;
    private boolean isActive;
    private long departmentId;
    private String departmentName;

    public DoctorDto(Department department, Doctor doctor) {
        id = doctor.getId();
        name = doctor.getUser().getName();
        licenceUrl = doctor.getLicenseImageUrl();
        isActive = doctor.isActive();
        departmentId = department.getId();
        departmentName = department.getName();
    }
}
