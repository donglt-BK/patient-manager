package com.bk.donglt.patient_manager.dto.hospital;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Location;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HospitalDto {
    private Long id;
    private String name;
    private Location location;
    private int managerCount;
    private AddressDto addressDto;
    private Status status;

    public HospitalDto(Hospital hospital) {
        id = hospital.getId();
        name = hospital.getName();
        location = hospital.getLocation();
        managerCount = hospital.getManagers().size();
        addressDto = new AddressDto(hospital.getAddress());
        status = hospital.getStatus();
    }
}
