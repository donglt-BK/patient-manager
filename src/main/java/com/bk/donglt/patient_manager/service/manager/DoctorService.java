package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService extends BaseService<Doctor, DoctorRepository> {
    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    Page<Doctor> findAvailable(Long departmentId, Pageable pageRequest) {
        return repository.findByDepartmentIdAndIsDeletedFalse(departmentId, pageRequest);
    }
/*
    private void processData(DoctorDto data) {
        User user = userService.findById(data.getUserId());
        data.setUser(user);

    }

    public Doctor addDoctor(DoctorDto newData, Department department) {
        Doctor doctor = new Doctor();
        doctor.update(newData);

        return save(doctor);
    }

    Doctor updateDoctor(DetailDoctorDto updateData) {
        Doctor doctor = findById(updateData.getId()).orElseThrow(BadRequestException::new);
        processData(updateData);
        doctor.update(updateData);
        return update(doctor);
    }

    public void activeDoctor(long doctorId, boolean active) {
        Doctor doctor = findById(doctorId);
        doctor.setIsActive(active);
        update(doctor);
    }*/
}
