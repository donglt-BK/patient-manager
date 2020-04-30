package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "conclusion")
public class Conclusion extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    private String conclusion;

    @OneToMany(mappedBy = "conclusion")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Treatment> treatments;
}
