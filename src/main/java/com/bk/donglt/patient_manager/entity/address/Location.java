package com.bk.donglt.patient_manager.entity.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Location {
    private Long latitude;
    private Long longitude;
}
