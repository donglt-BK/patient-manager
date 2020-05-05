package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "department")
public class Department extends BaseEntity {
    private String name;

    private Long hospitalId;

    @Column(name = "active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "department_manager",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private Set<User> managers;

    public boolean manageBy(User user) {
        if (managers.size() == 1) return managers.toArray(new User[1])[0].getId().equals(user.getId());

        return managers.stream().map(manager -> manager.getId().equals(user.getId())).reduce(Boolean::logicalOr).orElse(false);
    }

    public void update(DepartmentDataDto update) {
        if (update.getHospitalId() != null)
            hospitalId = update.getHospitalId();

        if (update.getName() != null)
            name = update.getName();

        if (update.getAddedManagers() != null)
            managers.addAll(update.getAddedManagers());

        List<Long> removeManagerIds = update.getRemovedManagerIds();
        if (removeManagerIds != null && removeManagerIds.size() != 0)
            managers.removeIf(manager -> removeManagerIds.contains(manager.getId()));
    }
}
