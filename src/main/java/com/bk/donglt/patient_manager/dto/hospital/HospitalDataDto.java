package com.bk.donglt.patient_manager.dto.hospital;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDataDto {
    private Long id;
    private String name;
    private String description;
    private Long latitude;
    private Long longitude;
    private AddressDto addressId;
    private Status status;

    private Address address;

}
