package com.bk.donglt.patient_manager.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    protected Date createdTime;

    @JsonIgnore
    protected Date updatedTime;

    @JsonIgnore
    protected Long createdByUserId;

    @JsonIgnore
    protected Long updatedByUserId;

    @JsonIgnore
    protected boolean isDeleted;
}
