package com.bk.donglt.patient_manager.dto.record;

import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.user.UserDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDataDto {
    private Long id;
    private Long appointmentId;
    private UserDto patient;
    private DoctorDto doctor;
    private List<SymptomDto> symptoms;
    private List<ConclusionDto> conclusions;
    private List<Long> removeSymptoms;
    private List<Long> removeConclusions;
}
