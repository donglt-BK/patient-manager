package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.enums.SymptomType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "symptom")
public class Symptom extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @Enumerated(EnumType.STRING)
    private SymptomType type;

    private String description;
}
