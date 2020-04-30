package com.bk.donglt.patient_manager.entity;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.enums.Gender;
import com.bk.donglt.patient_manager.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Role role;

    private String username;
    private String password;

    private String name;
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String job;
    private String race;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "country_id")),
            @AssociationOverride(name = "city", joinColumns = @JoinColumn(name = "city_id")),
            @AssociationOverride(name = "district", joinColumns = @JoinColumn(name = "district_id")),
            @AssociationOverride(name = "block", joinColumns = @JoinColumn(name = "block_id")),
    })
    private Address address;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "work_country_id")),
            @AssociationOverride(name = "city", joinColumns = @JoinColumn(name = "work_city_id")),
            @AssociationOverride(name = "district", joinColumns = @JoinColumn(name = "work_district_id")),
            @AssociationOverride(name = "block", joinColumns = @JoinColumn(name = "work_block_id")),
    })
    @AttributeOverride(name = "specificAddress", column = @Column(name = "work_specific_address"))
    private Address workAddress;

    private String phone;
    private String email;
}
