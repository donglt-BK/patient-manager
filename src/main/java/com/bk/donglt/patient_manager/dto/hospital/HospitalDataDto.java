package com.bk.donglt.patient_manager.dto.hospital;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDataDto {
    private Long id;
    private String name;
    private Long latitude;
    private Long longitude;
    private AddressDto addressId;

    private Address address;

}
