package com.bk.donglt.patient_manager.dto.record;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConclusionDto {
    private long id;
    private String conclusion;
    private List<TreatmentDto> treatments;
    private List<Long> removeTreatments;
}
