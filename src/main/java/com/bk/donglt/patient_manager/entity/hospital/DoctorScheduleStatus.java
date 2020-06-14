package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "doctor_schedule_status")
public class DoctorScheduleStatus extends BaseEntity {
    private Long scheduleId;
    private Long doctorId;
    private int currentBook;

    public void increase() {
        currentBook++;
    }

    public void decrease() {
        currentBook--;
    }

    public DoctorScheduleStatus(Long scheduleId, Doctor doctor) {
        this.scheduleId = scheduleId;
        doctorId = doctor.getId();
        currentBook = 0;
    }
}
