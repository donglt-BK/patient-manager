package com.bk.donglt.patient_manager.dto.user;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDataDto {
    private Long id;

    private String username;
    private String password;
    private String name;
    private Date dob;
    private Gender gender;

    private AddressDto address;
    private AddressDto workAddress;

    private Address formattedAddress;
    private Address formattedWorkAddress;

    private String phone;
    private String email;
}
