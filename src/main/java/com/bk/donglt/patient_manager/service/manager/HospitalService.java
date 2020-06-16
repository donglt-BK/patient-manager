package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Status;
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
    private AddressService addressService;

    @Autowired
    private UserService userService;

    public Page<Hospital> findSearchable(String name, Pageable pageRequest) {
        return repository.findByNameContainingAndStatusNotAndIsDeletedFalse(name, Status.HIDDEN, pageRequest);
    }

    Page<Hospital> findAll(Pageable pageRequest) {
        return repository.findByIsDeletedFalse(pageRequest);
    }

    private void processData(HospitalDataDto data) {
        if (data.getAddressId() != null)
            data.setAddress(addressService.build(data.getAddressId()));
    }

    Hospital addHospital(HospitalDataDto newData) {
        processData(newData);
        Hospital hospital = new Hospital();
        hospital.update(newData);
        return save(hospital);
    }

    Hospital updateHospital(Hospital hospital, HospitalDataDto updateData) {
        processData(updateData);
        hospital.update(updateData);
        return update(hospital);
    }

    User updateManager(Long hospitalId, Long managerId, boolean isAdd) {
        Hospital hospital = findById(hospitalId);
        User user = userService.findById(managerId);
        if (isAdd) hospital.addManager(user);
        else hospital.removeManager(user.getId());
        update(hospital);
        return user;
    }
}
