package com.bk.donglt.patient_manager.entity;

import com.bk.donglt.patient_manager.base.BaseEntity;
import com.bk.donglt.patient_manager.dto.user.UserDataDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.enums.Gender;
import com.bk.donglt.patient_manager.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "block", joinColumns = @JoinColumn(name = "block_id")),
    })
    private Address address;

    @Embedded
    @AssociationOverrides({
            @AssociationOverride(name = "block", joinColumns = @JoinColumn(name = "work_block_id")),
    })
    @AttributeOverride(name = "specificAddress", column = @Column(name = "work_specific_address"))
    private Address workAddress;

    private String phone;
    private String email;

    public User(UserDataDto userDto) {
        role = Role.USER;
        username = userDto.getUsername();
        password = userDto.getPassword();

        name = userDto.getName();
        dob = userDto.getDob();
        gender = userDto.getGender();

        address = userDto.getFormattedAddress();
        workAddress = userDto.getFormattedWorkAddress();

        phone = userDto.getPhone();
        email = userDto.getEmail();
    }
}
