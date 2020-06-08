package com.bk.donglt.patient_manager.dto.record;

import com.bk.donglt.patient_manager.enums.SymptomType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SymptomDto {
    private Long id;
    private SymptomType type;
    private String description;
}
