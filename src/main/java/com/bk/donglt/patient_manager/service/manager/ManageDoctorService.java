package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManageDoctorService extends BaseService<Doctor, DoctorRepository> {
    @Autowired
    private UserService userService;

    Page<Doctor> findAvailable(Long departmentId, Pageable pageRequest) {
        return repository.findByDepartmentIdAndIsDeletedFalse(departmentId, pageRequest);
    }

    Doctor addDoctor(DoctorDataDto newData) {
        Doctor doctor = new Doctor();
        doctor.setUser(userService.findById(newData.getUserId()));
        doctor.setLicenseImageUrl(newData.getLicenceUrl());
        doctor.setDepartmentId(newData.getDepartmentId());
        return save(doctor);
    }

    Doctor updateLicense(Doctor doctor, DoctorDataDto update) {
        if (!doctor.getDepartmentId().equals(update.getDepartmentId()))
            throw new BadRequestException("Doctor not in this department");

        doctor.setLicenseImageUrl(update.getLicenceUrl());
        return update(doctor);
    }

    void activeDoctor(long departmentId, long doctorId, boolean active) {
        Doctor doctor = findById(doctorId);
        if (!doctor.getDepartmentId().equals(departmentId))
            throw new BadRequestException("Doctor not in this department");

        doctor.setIsActive(active);
        update(doctor);
    }
}
