package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.enums.Shift;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "schedule_change_request")
public class ScheduleChangeRequest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(name = "resolve")
    private boolean isResolved;


    @Column(name = "approve")
    private boolean isApproved;
}
