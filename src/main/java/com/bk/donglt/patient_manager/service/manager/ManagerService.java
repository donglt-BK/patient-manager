package com.bk.donglt.patient_manager.service.manager;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.department.DepartmentDataDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDetailDto;
import com.bk.donglt.patient_manager.dto.department.DepartmentDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDataDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDataDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDetailDto;
import com.bk.donglt.patient_manager.dto.hospital.HospitalDto;
import com.bk.donglt.patient_manager.dto.manage.ManagerChangeDto;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
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

    public Page<HospitalDto> getHospitals(Pageable pageable) {
        checkUserAuthorize();
        return hospitalService.findAvailable(pageable).map(HospitalDto::new);
    }

    public HospitalDetailDto getHospital(long hospitalId) {
        Hospital hospital = hospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        return new HospitalDetailDto(hospital);
    }

    public HospitalDetailDto addHospital(HospitalDataDto hospital) {
        checkUserAuthorize();
        return new HospitalDetailDto(hospitalService.addHospital(hospital));
    }

    public HospitalDetailDto updateHospital(HospitalDataDto update) {
        Hospital hospital = hospitalService.findById(update.getId());
        checkUserAuthorize(hospital);
        return new HospitalDetailDto(hospitalService.updateHospital(hospital, update));
    }

    public HospitalDetailDto updateHospitalManager(ManagerChangeDto update) {
        checkUserAuthorize();
        return new HospitalDetailDto(hospitalService.updateManager(update));
    }

    public void activeHospital(long hospitalId, boolean active) {
        checkUserAuthorize();
        hospitalService.activeHospital(hospitalId, active);
    }

    public void deleteHospital(long hospitalId) {
        checkUserAuthorize();
        hospitalService.delete(hospitalId);
    }

    //------------------------------------------------------------------------------------------------------------------
    public Page<DepartmentDto> getDepartments(long hospitalId, Pageable pageable) {
        Hospital hospital = hospitalService.findById(hospitalId);
        checkUserAuthorize(hospital);
        return departmentService
                .findAvailable(hospital.getId(), pageable)
                .map(department -> new DepartmentDto(hospital, department));
    }

    public DepartmentDetailDto getDepartment(long departmentId) {
        Department department = departmentService.findById(departmentId);
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
        return new DepartmentDetailDto(hospital, department);
    }

    public DepartmentDetailDto addDepartment(DepartmentDataDto department) {
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        return new DepartmentDetailDto(hospital, departmentService.addDepartment(department));
    }

    public DepartmentDetailDto updateDepartment(DepartmentDataDto update) {
        Department department = departmentService.findById(update.getId());
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
        return new DepartmentDetailDto(hospital, departmentService.updateDepartment(department, update));
    }

    public DepartmentDetailDto updateDepartmentManager(ManagerChangeDto update) {
        Department department = departmentService.findById(update.getDepartmentId());
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        return new DepartmentDetailDto(hospital, departmentService.updateDepartment(department, update));
    }

    public void activeDepartment(long departmentId, boolean active) {
        Department department = departmentService.findById(departmentId);
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital);
        departmentService.activeDepartment(department, active);
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
                .findAvailable(department.getId(), pageable)
                .map(doctor -> new DoctorDto(department, doctor));
    }

    public DoctorDto getDoctor(long doctorId) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department, doctor);
        return new DoctorDto(department, doctor);
    }

    public DoctorDto addDoctor(DoctorDataDto doctor) {
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);
        return new DoctorDto(department, doctorService.addDoctor(doctor));
    }

    public DoctorDto updateLicense(DoctorDataDto update) {
        Doctor doctor = doctorService.findById(update.getId());
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department, doctor);
        return new DoctorDto(department, doctorService.updateLicense(doctor, update));
    }

    public void activeDoctor(long doctorId, boolean active) {
        Doctor doctor = doctorService.findById(doctorId);
        Department department = departmentService.findById(doctor.getDepartmentId());
        checkUserAuthorize(department);
        doctorService.activeDoctor(doctor, active);
    }

    public void deleteDoctor(long departmentId, long doctorId) {
        Department department = departmentService.findById(departmentId);
        checkUserAuthorize(department);
        doctorService.delete(doctorId);
    }
}
