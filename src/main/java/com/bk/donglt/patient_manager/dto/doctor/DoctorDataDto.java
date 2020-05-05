package com.bk.donglt.patient_manager.dto.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDataDto {
    private long id;
    private long departmentId;
    private long userId;
    private String licenceUrl;
}
