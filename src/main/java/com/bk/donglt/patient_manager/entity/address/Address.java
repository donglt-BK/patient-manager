package com.bk.donglt.patient_manager.entity.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
public class Address {
    @ManyToOne
    private Block block;

    private String specificAddress;
}