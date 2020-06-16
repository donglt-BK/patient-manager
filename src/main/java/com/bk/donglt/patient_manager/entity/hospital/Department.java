package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "department")
public class Department extends BaseEntity {
    private String name;
    private String description;

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
        status = update.getStatus();
        if (update.getDescription() != null)
            description = update.getDescription();

        if (update.getName() != null)
            name = update.getName();

        if (update.getHospitalId() != null)
            hospitalId = update.getHospitalId();
    }

    public void addManager(User user) {
        managers.add(user);
    }

    public void removeManager(Long userId) {
        managers.removeIf(u -> u.getId().equals(userId));
    }
}
