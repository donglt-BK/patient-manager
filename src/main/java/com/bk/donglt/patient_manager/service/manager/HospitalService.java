package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.HospitalDto;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.HospitalRepository;
import com.bk.donglt.patient_manager.service.UserService;
import com.bk.donglt.patient_manager.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HospitalService extends BaseService<Hospital, HospitalRepository> {
    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    Page<Hospital> findAvailable(Pageable pageRequest) {
        return repository.findByIsActiveFalseAndIsDeletedFalse(pageRequest);
    }

    private void processData(HospitalDto dto) {
        dto.setAddress(addressService.build(dto.getAddressDto()));
        dto.setAddManagers(userService.findUsers(dto.getAddManagerIds()));
    }

    public Hospital addHospital(HospitalDto newData) {
        processData(newData);
        Hospital hospital = new Hospital();
        hospital.update(newData);

        return save(hospital);
    }

    Hospital updateHospital(HospitalDto updateData) {
        Hospital hospital = repository.findById(updateData.getId()).orElseThrow(BadRequestException::new);
        processData(updateData);
        hospital.update(updateData);
        return update(hospital);
    }

    public void activeHospital(long hospitalId, boolean active) {
        Hospital hospital = repository.findById(hospitalId).orElseThrow(BadRequestException::new);
        hospital.setIsActive(active);

        update(hospital);
    }


    public void deleteHospital(long hospitalId) {
        Hospital hospital = repository.findById(hospitalId).orElseThrow(BadRequestException::new);
        hospital.setDeleted(true);

        update(hospital);
    }
}
