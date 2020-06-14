package com.bk.donglt.patient_manager.dto;

import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.dto.user.UserDataDto;
import com.bk.donglt.patient_manager.enums.Shift;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AppointmentDto {
    private long id;

    private UserDataDto user;

    private Date date;
    private Shift shift;

    private HospitalDto hospital;
    private DepartmentDto department;
    private DoctorDto doctor;

    private Long recordId;

    private boolean isReceived;
    private boolean isCanceled;
}
