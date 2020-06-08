package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Appointment;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "record")
public class Record extends BaseEntity {
    @OneToOne(mappedBy = "record")
    private Appointment appointment;

    private Long patientId;
    private Long doctorId;

    @OneToMany(mappedBy = "record")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Symptom> symptoms;


    @OneToMany(mappedBy = "record")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Conclusion> conclusions;
}
