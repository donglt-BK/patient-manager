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
@Table(name = "district")
public class District extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private City city;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof District)) {
            return false;
        }
        return ((District) obj).getId().longValue() == id.longValue();
    }
}