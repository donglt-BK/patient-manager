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
@Table(name = "block")
public class Block extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private District district;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Block)) {
            return false;
        }
        return ((Block) obj).getId().longValue() == id.longValue();
    }
}
