package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.enums.TreatmentType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "treatment")
public class Treatment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "conclusion_id")
    private Conclusion conclusion;

    @Enumerated(EnumType.STRING)
    private TreatmentType type;

    private String description;
    private Date from;
    private Date to;
}
