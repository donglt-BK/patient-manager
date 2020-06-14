package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.enums.Shift;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    private Long departmentId;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doctor_schedule",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")}
    )
    private Set<Doctor> doctors;

    @Column(name = "schedule_limit")
    private int scheduleLimit;

    @Column(name = "doctor_limit")
    private int doctorLimit;

    public void addDoctor(Doctor doctor) {
        if (doctors == null) doctors = new HashSet<>();
        doctors.add(doctor);
    }

    public void removeDoctor(Doctor removedDoctor) {
        if (doctors != null) doctors.removeIf(d -> d.getId().equals(removedDoctor.getId()));
    }

    public boolean contain(Doctor doctor) {
        if (doctors == null) return false;
        for (Doctor d : doctors) {
            if (d.getId().equals(doctor.getId())) return true;
        }
        return false;
    }

    public boolean contain(Long doctorId) {
        for (Doctor d : doctors) {
            if (d.getId().equals(doctorId)) return true;
        }
        return false;
    }
}
