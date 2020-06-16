package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.dto.user.UserDetailDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Role;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService extends BaseService<Doctor, DoctorRepository> {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DoctorService doctorService;

    public Page<HospitalDto> getHospitals(Pageable pageable) {
        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN)) {
            return hospitalService.findAll(pageable).map(HospitalDto::new);
        } else {
            UserDetailDto user = getCurrentUser().getUser();
            if (user.getManageHospitalIds().size() > 0) {
                return hospitalService.findByIdIn(user.getManageHospitalIds(), pageable).map(HospitalDto::new);
            } else {
                return hospitalService.findByIdIn(user.getManageDepartmentHospitalId(), pageable).map(HospitalDto::new);
            }
        }
    }

    public HospitalDto addHospital(HospitalDataDto hospital) {
        checkUserAuthorize();
        return new HospitalDto(hospitalService.addHospital(hospital));
    }

    public HospitalDto updateHospital(HospitalDataDto update) {
        Hospital hospital = hospitalService.findById(update.getId());
        checkUserAuthorize(hospital);
        return new HospitalDto(hospitalService.updateHospital(hospital, update));
    }

    public User changeHospitalManager(Long hospitalId, Long managerId, boolean isAdd) {
        checkUserAuthorize();
        return hospitalService.updateManager(hospitalId, managerId, isAdd);
    }

    public void deleteHospital(long hospitalId) {
        checkUserAuthorize();
        hospitalService.delete(hospitalId);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<DepartmentDto> getDepartments(long hospitalId, Pageable pageable) {
        Hospital hospital = hospitalService.findById(hospitalId);
        UserDetailDto user = getCurrentUser().getUser();
        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN) || user.getManageHospitalIds().contains(hospitalId))
            return departmentService
                    .findAll(hospital.getId(), pageable)
                    .map(department -> new DepartmentDto(hospital, department));
        else
            return departmentService.findByIdIn(user.getManageDepartmentIds(), pageable)
                    .map(department -> new DepartmentDto(hospital, department));
    }

    public DepartmentDto addDepartment(DepartmentDataDto department) {
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        return new DepartmentDto(hospital, departmentService.addDepartment(department));
    }

    public DepartmentDto updateDepartment(DepartmentDataDto update) {
        Department department = departmentService.findById(update.getId());
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
        return new DepartmentDto(hospital, departmentService.updateDepartment(department, update));
    }

    public User changeDepartmentManager(Long departmentId, Long managerId, boolean isAdd) {
        Department department = departmentService.findById(departmentId);
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        return departmentService.updateManager(departmentId, managerId, isAdd);
    }

    public void deleteDepartment(long hospitalId, long departmentId) {
        Hospital hospital = hospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        departmentService.delete(departmentId);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<DoctorDto> getDoctors(long departmentId, Pageable pageable) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return doctorService
                .findAll(department.getId(), pageable)
                .map(doctor -> new DoctorDto(department, doctor));
    }

    public List<Long> getAllDoctorId(long departmentId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return doctorService.findDoctorIds(department.getId());
    }

    public DoctorDto addDoctor(Long departmentId, Long userId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return new DoctorDto(department, doctorService.addDoctor(departmentId, userId));
    }

    public DoctorDto updateDoctor(DoctorDataDto update) {
        Doctor doctor = doctorService.findById(update.getId());
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department, doctor);
        return new DoctorDto(department, doctorService.updateDoctor(doctor, update));
    }

    public void deleteDoctor(long departmentId, long doctorId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        doctorService.delete(doctorId);
    }

    //------------------------------------------------------------------------------------------------------------------

    public List<HospitalDto> findHospitalIdIn() {
        UserDetailDto user = getCurrentUser().getUser();
        List<Hospital> hospitals;
        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN)) {
            hospitals = hospitalService.findAll();
        } else {
            if (user.getManageHospitalIds().size() > 0) {
                hospitals = hospitalService.findByIdIn(user.getManageHospitalIds());
            } else {
                hospitals = hospitalService.findByIdIn(user.getManageDepartmentHospitalId());
            }
        }
        return hospitals.stream().map(HospitalDto::new).collect(Collectors.toList());
    }

    public List<DepartmentDto> findDepartmentIdIn(Long hospitalId) {
        UserDetailDto user = getCurrentUser().getUser();
        List<Department> departments;
        Hospital hospital = hospitalService.findById(hospitalId);

        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN) || user.getManageHospitalIds().contains(hospitalId))
            departments = departmentService.findAll(hospitalId);
        else if (user.getManageDepartmentHospitalId().contains(hospitalId))
            departments = departmentService.findByIdIn(user.getManageDepartmentIds());
        else throw new BadRequestException("You don't have permission on this hospital");

        return departments.stream()
                .filter(d -> d.getHospitalId().equals(hospitalId))
                .map(d -> new DepartmentDto(hospital, d))
                .collect(Collectors.toList());
    }

    public List<Doctor> findAllDoctor(Long departmentId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        return doctorService.findAll(department.getId());
    }
}
