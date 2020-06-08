package com.bk.donglt.patient_manager.dto.doctor;

import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.enums.Status;
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
    private long departmentId;
    private String departmentName;
    private Status status;

    public DoctorDto(Department department, Doctor doctor) {
        id = doctor.getId();
        name = doctor.getUser().getName();
        licenceUrl = doctor.getLicenseImageUrl();
        departmentId = department.getId();
        departmentName = department.getName();
        status = doctor.getStatus();
    }
}
