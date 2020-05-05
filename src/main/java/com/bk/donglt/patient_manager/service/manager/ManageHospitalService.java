package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.repository.HospitalRepository;
import com.bk.donglt.patient_manager.service.UserService;
import com.bk.donglt.patient_manager.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManageHospitalService extends BaseService<Hospital, HospitalRepository> {
    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    Page<Hospital> findAvailable(Pageable pageRequest) {
        return repository.findByIsDeletedFalse(pageRequest);
    }

    private void processData(HospitalDataDto data) {
        if (data.getAddedManagerIds() != null)
            data.setAddedManagers(userService.findUsers(data.getAddedManagerIds()));
        if (data.getAddressId() != null)
            data.setAddress(addressService.build(data.getAddressId()));
    }

    Hospital addHospital(HospitalDataDto newData) {
        processData(newData);
        Hospital hospital = new Hospital();
        hospital.update(newData);
        return save(hospital);
    }

    Hospital updateHospital(HospitalDataDto updateData) {
        Hospital hospital = findById(updateData.getId());
        processData(updateData);
        hospital.update(updateData);
        return update(hospital);
    }

    void activeHospital(long hospitalId, boolean active) {
        Hospital hospital = findById(hospitalId);
        hospital.setIsActive(active);
        update(hospital);
    }
}
