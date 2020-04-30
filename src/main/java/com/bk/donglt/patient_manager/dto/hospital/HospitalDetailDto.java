package com.bk.donglt.patient_manager.dto.hospital;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Location;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HospitalDetailDto {
    private Long id;
    private String name;
    private Location location;
    private List<User> managers;
    private AddressDto address;

    public HospitalDetailDto(Hospital hospital) {
        id = hospital.getId();
        name = hospital.getName();
        location = hospital.getLocation();
        managers = new ArrayList<>(hospital.getManagers());
        address = new AddressDto(hospital.getAddress());
    }
}
