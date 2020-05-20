package com.bk.donglt.patient_manager.entity.hospital;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.entity.address.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    @Column(name = "active")
    private Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hospital_manager",
            joinColumns = @JoinColumn(name = "hospital_id"),
            inverseJoinColumns = {@JoinColumn(name = "manager_id")}
    )
    private Set<User> managers;

    public boolean manageBy(Long userId) {
        return managers.stream()
                .map(manager -> manager.getId().equals(userId))
                .reduce(Boolean::logicalOr).orElse(false);
    }

    public void update(HospitalDataDto update) {
        if (update.getName() != null) name = update.getName();

        if (location == null) location = new Location();
        if (update.getLatitude() != null) location.setLatitude(update.getLatitude());
        if (update.getLongitude() != null) location.setLongitude(update.getLongitude());

        if (update.getAddress() != null) address = update.getAddress();

        if (update.getAddedManagers() != null) managers.addAll(update.getAddedManagers());

        List<Long> removeManagerIds = update.getRemovedManagerIds();
        if (removeManagerIds != null && removeManagerIds.size() != 0)
            managers.removeIf(manager -> removeManagerIds.contains(manager.getId()));
    }
}
