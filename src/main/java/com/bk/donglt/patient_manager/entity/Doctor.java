package com.bk.donglt.patient_manager.entity;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "doctor")
public class Doctor extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long departmentId;

    private String licenseImageUrl;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Doctor() {
        status = Status.UNAVAILABLE;
    }
}
