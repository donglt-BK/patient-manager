package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.enums.Status;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService extends BaseService<Doctor, DoctorRepository> {
    @Autowired
    private UserService userService;

    public Page<Doctor> findSearchable(Long departmentId, String name, Pageable pageRequest) {
        return repository.findByDepartmentIdAndUser_NameContainingAndStatusNotAndIsDeletedFalse(departmentId, name, Status.HIDDEN, pageRequest);
    }

    Page<Doctor> findAll(Long departmentId, Pageable pageRequest) {
        return repository.findByDepartmentIdAndIsDeletedFalse(departmentId, pageRequest);
    }


    List<Doctor> findAll(Long departmentId) {
        return repository.findByDepartmentIdAndIsDeletedFalse(departmentId);
    }

    List<Long> findDoctorIds(Long departmentId) {
        return repository.findDoctorIds(departmentId);
    }

    Doctor addDoctor(Long departmentId, Long userId) {
        Doctor doctor = repository.findByUser_IdAndDepartmentIdAndIsDeletedFalse(userId, departmentId);
        if (doctor != null) throw new BadRequestException("This user already a doctor in this department");

        doctor = new Doctor();
        doctor.setUser(userService.findById(userId));
        doctor.setDepartmentId(departmentId);
        doctor.setStatus(Status.HIDDEN);
        return save(doctor);
    }

    Doctor updateDoctor(Doctor doctor, DoctorDataDto update) {
        doctor.setStatus(update.getStatus());
        doctor.setLicenseImageUrl(update.getLicenceUrl());
        return update(doctor);
    }

    public Doctor findMeInDepartment(Long departmentId) {
        Doctor doctor = repository.findByUser_IdAndDepartmentIdAndIsDeletedFalse(getCurrentUser().getUser().getId(), departmentId);
        if (doctor == null) throw new BadRequestException("User is not doctor of this department");
        return doctor;
    }
}
