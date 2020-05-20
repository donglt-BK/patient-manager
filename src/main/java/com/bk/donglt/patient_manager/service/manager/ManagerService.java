package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDetailDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDetailDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.entity.Doctor;
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
    private static boolean disableSecurity = true;
    @Autowired
    private ManageHospitalService manageHospitalService;

    @Autowired
    private ManageDepartmentService manageDepartmentService;

    @Autowired
    private ManageDoctorService manageDoctorService;

    public Page<HospitalDto> getHospitals(Pageable pageable) {
        checkUserAuthorize();
        return manageHospitalService.findAvailable(pageable).map(HospitalDto::new);
    }

    public HospitalDetailDto getHospital(long hospitalId) {
        Hospital hospital = manageHospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        return new HospitalDetailDto(hospital);
    }

    public HospitalDetailDto addHospital(HospitalDataDto hospital) {
        checkUserAuthorize();
        return new HospitalDetailDto(manageHospitalService.addHospital(hospital));
    }

    public HospitalDetailDto updateHospital(HospitalDataDto update) {
        Hospital hospital = manageHospitalService.findById(update.getId());
        checkUserAuthorize(hospital);
        return new HospitalDetailDto(manageHospitalService.updateHospital(hospital, update));
    }

    public void activeHospital(long hospitalId, boolean active) {
        checkUserAuthorize();
        manageHospitalService.activeHospital(hospitalId, active);
    }

    public void deleteHospital(long hospitalId) {
        checkUserAuthorize();
        manageHospitalService.delete(hospitalId);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<DepartmentDto> getDepartments(long hospitalId, Pageable pageable) {
        Hospital hospital = manageHospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        return manageDepartmentService.findAvailable(hospital.getId(), pageable).map(department -> {
            DepartmentDto dto = new DepartmentDto(department);
            dto.setHospitalName(hospital.getName());
            return dto;
        });
    }

    public DepartmentDetailDto getDepartment(long departmentId) {
        Department department = manageDepartmentService.findById(departmentId);
        Hospital hospital = manageHospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
        return buildDepartmentDto(hospital, department);
    }

    public DepartmentDetailDto addDepartment(DepartmentDataDto department) {
        Hospital hospital = manageHospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        return buildDepartmentDto(hospital, manageDepartmentService.addDepartment(department));
    }

    public DepartmentDetailDto updateDepartment(DepartmentDataDto update) {
        Hospital hospital = manageHospitalService.findById(update.getHospitalId());
        Department department = manageDepartmentService.findById(update.getId());
        checkUserAuthorize(hospital, department);
        return buildDepartmentDto(hospital, manageDepartmentService.updateDepartment(department, update));
    }

    public void activeDepartment(long hospitalId, long departmentId, boolean active) {
        Hospital hospital = manageHospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        manageDepartmentService.activeDepartment(hospitalId, departmentId, active);
    }

    public void deleteDepartment(long hospitalId, long departmentId) {
        Hospital hospital = manageHospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        manageDepartmentService.delete(departmentId);
    }

    private DepartmentDetailDto buildDepartmentDto(Hospital hospital, Department department) {
        DepartmentDetailDto dto = new DepartmentDetailDto(department);
        dto.setHospitalName(hospital.getName());
        return dto;
    }
    //------------------------------------------------------------------------------------------------------------------
    public Page<DoctorDto> getDoctors(long departmentId, Pageable pageable) {
        Department department = manageDepartmentService.findById(departmentId);
        checkUserAuthorize(department);
        return manageDoctorService.findAvailable(department.getId(), pageable).map(doctor -> buildDoctorDto(department, doctor));
    }

    public DoctorDto getDoctor(long doctorId) {
        Doctor doctor = manageDoctorService.findById(doctorId);
        Department department = manageDepartmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department, doctor);
        return buildDoctorDto(department, doctor);
    }

    public DoctorDto addDoctor(DoctorDataDto doctor) {
        Department department = manageDepartmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);
        return buildDoctorDto(department, manageDoctorService.addDoctor(doctor));
    }

    public DoctorDto updateLicense(DoctorDataDto update) {
        Doctor doctor = manageDoctorService.findById(update.getId());
        Department department = manageDepartmentService.findById(update.getDepartmentId());
        checkUserAuthorize(department, doctor);
        return buildDoctorDto(department, manageDoctorService.updateLicense(doctor, update));
    }

    public void activeDoctor(long departmentId, long doctorId, boolean active) {
        Department department = manageDepartmentService.findById(departmentId);
        checkUserAuthorize(department);
        manageDoctorService.activeDoctor(departmentId, doctorId, active);
    }

    public void deleteDoctor(long departmentId, long doctorId) {
        Department department = manageDepartmentService.findById(departmentId);
        checkUserAuthorize(department);
        manageDoctorService.delete(doctorId);
    }

    private DoctorDto buildDoctorDto(Department department, Doctor doctor) {
        DoctorDto dto = new DoctorDto(doctor);
        dto.setDepartmentName(department.getName());
        return dto;
    }
    //------------------------------------------------------------------------------------------------------------------
    private void checkUserAuthorize() {
        checkUserAuthorize(null, null, null);
    }

    private void checkUserAuthorize(Hospital hospital) {
        checkUserAuthorize(hospital, null);
    }

    private void checkUserAuthorize(Department department) {
        Hospital hospital = manageHospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
    }

    private void checkUserAuthorize(Department department, Doctor doctor) {
        Hospital hospital = manageHospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department, doctor);
    }

    private void checkUserAuthorize(Hospital hospital, Department department) {
        checkUserAuthorize(hospital, department, null);
    }

    private void checkUserAuthorize(Hospital hospital, Department department, Doctor doctor) {
        if (disableSecurity) return;
        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN)) return;

        CustomUserDetails userAuthenticate = getCurrentUser();
        Long userId = userAuthenticate.getUser().getId();

        if (hospital != null && hospital.manageBy(userId)) return;

        if (department != null && department.manageBy(userId)) return;

        if (doctor != null && doctor.getUser().getId().equals(userId)) return;

        throw new UnAuthorizeException();
    }
}
