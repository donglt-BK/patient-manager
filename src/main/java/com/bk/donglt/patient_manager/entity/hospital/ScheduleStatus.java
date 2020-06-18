package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "schedule_status")
public class ScheduleStatus extends BaseEntity {
    private Long scheduleId;
    private int currentBook;
    private int pos;

    public void increase() {
        currentBook++;
        pos++;
    }

    public void decrease() {
        currentBook--;
    }
}
