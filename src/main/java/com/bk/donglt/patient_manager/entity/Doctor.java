package com.bk.donglt.patient_manager.entity;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "doctor")
public class Doctor extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private String licenseImageUrl;

    @Column(name = "active")
    private Boolean isActive;

    private void update(DoctorDto doctorDto) {
        user = doctorDto.getUser();
        department = doctorDto.getDepartment();
        licenseImageUrl = doctorDto.getLicenceUrl();
    }
}
