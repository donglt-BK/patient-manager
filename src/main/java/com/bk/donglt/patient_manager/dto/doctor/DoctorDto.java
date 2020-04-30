package com.bk.donglt.patient_manager.dto.doctor;

import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {
    private long id;
    private long userId;
    private String name;
    private String licenceUrl;
    private boolean isActive;
    private Department department;

    @JsonIgnore
    private User user;

    public DoctorDto(Doctor doctor) {
        id = doctor.getId();
        name = doctor.getUser().getName();
        isActive = doctor.getIsActive();
    }

    public DoctorDto(Doctor doctor, boolean showDepartment) {
        this(doctor);

        department = doctor.getDepartment();
    }
}
