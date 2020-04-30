package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.HospitalDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.entity.address.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hospital")
public class Hospital extends BaseEntity {
    private String name;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "hospital_manager",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private List<User> managers;

    public boolean manageBy(User user) {
        if (managers.size() == 1) return managers.get(0).getId().equals(user.getId());

        return managers.stream().map(manager -> manager.getId().equals(user.getId())).reduce(Boolean::logicalOr).orElse(false);
    }

    public void update(HospitalDto update) {
        name = update.getName();
        address = update.getAddress();
        location = update.getLocation();

        managers.addAll(update.getAddManagers());

        List<Long> removeManagerIds = update.getRemoveManagerIds();
        if (removeManagerIds.size() == 0) return;
        managers.removeIf(manager -> removeManagerIds.contains(manager.getId()));
    }
}
