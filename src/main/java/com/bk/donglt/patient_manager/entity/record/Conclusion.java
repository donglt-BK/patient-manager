package com.bk.donglt.patient_manager.entity.record;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.record.ConclusionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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


    public Conclusion(Record record, ConclusionDto dto) {
        this.record = record;
        conclusion = dto.getConclusion();
    }
}
