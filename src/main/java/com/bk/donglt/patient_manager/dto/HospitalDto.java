package com.bk.donglt.patient_manager.dto;

import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.entity.address.Location;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HospitalDto {
    private Long id;
    private String name;
    private Location location;

    private AddressDto addressDto;

    private List<Long> addManagerIds;
    private List<Long> removeManagerIds;

    private Address address;
    private List<User> addManagers;

    public HospitalDto(Hospital hospital) {
        id = hospital.getId();
        name = hospital.getName();
        addressDto = new AddressDto(hospital.getAddress());

        location = hospital.getLocation();
    }
}
