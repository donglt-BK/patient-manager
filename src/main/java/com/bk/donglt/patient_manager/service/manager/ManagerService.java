package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
import com.bk.donglt.patient_manager.dto.HospitalDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Role;
import com.bk.donglt.patient_manager.exception.UnAuthorizeException;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManagerService extends BaseService<Doctor, DoctorRepository> {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    public Page<Hospital> getHospitals(Pageable pageable) {
        checkUserAuthorize();
        return hospitalService.findAvailable(pageable);
    }

    public Hospital addHospital(HospitalDto hospital) {
        checkUserAuthorize();
        return hospitalService.addHospital(hospital);
    }

    public Hospital updateHospital(HospitalDto update) {
        checkUserAuthorize();
        return hospitalService.updateHospital(update);
    }

    public void activeHospital(long hospitalId, boolean active) {
        checkUserAuthorize();
        hospitalService.activeHospital(hospitalId, active);
    }

    public void deleteHospital(long hospitalId) {
        checkUserAuthorize();
        hospitalService.deleteHospital(hospitalId);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<Department> getDepartments(long hospitalId, Pageable pageable) {
        Hospital hospital = hospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        return departmentService.findAvailable(hospital.getId(), pageable);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<Doctor> getDoctors(long departmentId, Pageable pageable) throws UnAuthorizeException {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return doctorService.findAvailable(department.getId(), pageable);
    }

    //------------------------------------------------------------------------------------------------------------------
    private void checkUserAuthorize() throws UnAuthorizeException {
        checkUserAuthorize(null, null);
    }

    private void checkUserAuthorize(Hospital hospital) throws UnAuthorizeException {
        checkUserAuthorize(hospital, null);
    }

    private void checkUserAuthorize(Department department) throws UnAuthorizeException {
        checkUserAuthorize(department.getHospital(), department);
    }

    private void checkUserAuthorize(Hospital hospital, Department department) throws UnAuthorizeException {
        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN)) return;

        CustomUserDetails userAuthenticate = getCurrentUser();
        User user = userAuthenticate.getUser();

        if (hospital != null) {
            if (userAuthenticate.hasRole(Role.HOSPITAL_MANAGER) && hospital.manageBy(user)) return;
        }

        if (department != null) {
            if (userAuthenticate.hasRole(Role.DEPARTMENT_MANAGER) && department.manageBy(user)) return;
        }

        throw new UnAuthorizeException();
    }
}
