package com.bk.donglt.patient_manager.dto.doctor;

import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDataDto {
    private long id;
    private long departmentId;
    private long userId;
    private String licenceUrl;
    private Status status;
}
