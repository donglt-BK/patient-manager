package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.enums.Shift;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    private Date date;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @ManyToMany
    @JoinTable(
            name = "doctor_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")}
    )
    private List<Doctor> doctors;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private int patientPerDoctor;
}
