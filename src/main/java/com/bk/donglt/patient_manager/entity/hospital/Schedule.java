package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.enums.Shift;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule extends BaseEntity {
    private Long departmentId;

    private Date date;

    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(name = "`limit`")
    private int limit;

    @Column(name = "close")
    private boolean isClosed;

    @Transient
    private int bookingStatus;

    @Transient
    private int pos;
}
