package com.bk.donglt.patient_manager.dto.record;

import com.bk.donglt.patient_manager.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TreatmentDto {
    private Long id;
    private TreatmentType type;
    private String description;
    private Date from;
    private Date to;
}
