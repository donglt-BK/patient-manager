package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Appointment;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "record")
public class Record extends BaseEntity {
    @OneToOne(mappedBy = "record")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "record")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Symptom> symptoms;


    @OneToMany(mappedBy = "record")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Conclusion> conclusions;
}
