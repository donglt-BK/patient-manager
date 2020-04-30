package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "department")
public class Department extends BaseEntity {
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(name = "active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "department_manager",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private List<User> managers;

    public boolean manageBy(User user) {
        if (managers.size() == 1) return managers.get(0).getId().equals(user.getId());

        return managers.stream().map(manager -> manager.getId().equals(user.getId())).reduce(Boolean::logicalOr).orElse(false);
    }
}
