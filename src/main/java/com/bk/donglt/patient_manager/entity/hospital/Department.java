package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.manage.ManagerChangeDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.enums.Status;
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

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "department_manager",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private Set<User> managers;

    public Department() {
        status = Status.UNAVAILABLE;
    }

    public boolean manageBy(Long userId) {
        return managers.stream()
                .map(manager -> manager.getId().equals(userId))
                .reduce(Boolean::logicalOr).orElse(false);
    }

    public void update(DepartmentDataDto update) {
        if (update.getHospitalId() != null)
            hospitalId = update.getHospitalId();

        if (update.getName() != null)
            name = update.getName();
    }

    public void update(ManagerChangeDto update) {
        if (update.getAddedManagers() != null) managers.addAll(update.getAddedManagers());

        List<Long> removeManagerIds = update.getRemovedManagerIds();
        if (removeManagerIds != null && removeManagerIds.size() != 0)
            managers.removeIf(manager -> removeManagerIds.contains(manager.getId()));
    }
}
