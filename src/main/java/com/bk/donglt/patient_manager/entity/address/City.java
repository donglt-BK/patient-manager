package com.bk.donglt.patient_manager.entity.address;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "city")
public class City extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonIgnore
    private Country country;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof City)) {
            return false;
        }
        return ((City) obj).getId().longValue() == id.longValue();
    }
}
