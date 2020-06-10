package com.bk.donglt.patient_manager.dto.record;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDataDto {
    private Long id;
    private Long appointmentId;
    private Long patientId;
    private Long doctorId;
    private List<SymptomDto> symptoms;
    private List<ConclusionDto> conclusions;
    private List<Long> removeSymptoms;
    private List<Long> removeConclusions;
}
