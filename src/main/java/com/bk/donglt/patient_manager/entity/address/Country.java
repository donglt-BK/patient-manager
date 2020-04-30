package com.bk.donglt.patient_manager.entity.address;

import com.bk.donglt.patient_manager.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "country")
public class Country extends BaseEntity {
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Country)) {
            return false;
        }
        return ((Country) obj).getId().longValue() == id.longValue();
    }
}

