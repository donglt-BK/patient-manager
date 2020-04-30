package com.bk.donglt.patient_manager.dto.hospital;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HospitalDataDto {
    private Long id;
    private String name;
    private Long latitude;
    private Long longitude;
    private AddressDto addressId;

    private List<Long> addedManagerIds;
    private List<Long> removedManagerIds;

    private List<User> addedManagers;
    private Address address;

}
