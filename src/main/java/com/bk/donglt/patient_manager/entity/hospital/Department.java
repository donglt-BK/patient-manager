package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
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
@Table(name = "department")
public class Department extends BaseEntity {
    private String name;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "department_manager",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private List<User> managers;

    @OneToMany(mappedBy = "department")
    @Fetch(FetchMode.SUBSELECT)
    @Where(clause = "is_deleted = false")
    private List<Doctor> doctors;

    public boolean manageBy(User user) {
        if (managers.size() == 1) return managers.get(0).getId().equals(user.getId());

        return managers.stream().map(manager -> manager.getId().equals(user.getId())).reduce(Boolean::logicalOr).orElse(false);
    }
}
