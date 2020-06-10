package com.bk.donglt.patient_manager.base;

import com.bk.donglt.patient_manager.config.sercurity.CurrentUserDetailsContainer;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.hospital.Department;
import com.bk.donglt.patient_manager.entity.hospital.Hospital;
import com.bk.donglt.patient_manager.enums.Role;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.exception.UnAuthorizeException;
import com.bk.donglt.patient_manager.service.manager.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public abstract class BaseService<E extends BaseEntity, R extends BaseRepository<E>> {
    @Autowired
    protected R repository;

    @Autowired
    private CurrentUserDetailsContainer currentUserDetailsContainer;

    public CustomUserDetails getCurrentUser() {
        return this.currentUserDetailsContainer.getUserDetails();
    }

    public E save(E entity) {
        if (entity == null) {
            return null;
        }
        setCreateUser(entity);
        return repository.save(entity);
    }

    public List<E> save(List<E> entities) {
        entities.forEach(this::setCreateUser);
        return repository.saveAll(entities);
    }

    public E findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Id not found"));
    }


    public List<E> findByIdIn(Collection<Long> ids) {
        return repository.findByIdIn(ids);
    }

    public E update(E entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        } else {
            setUpdateUser(entity);
            return repository.save(entity);
        }
    }

    public List<E> update(List<E> entities) {
        if (entities == null) {
            return null;
        } else {
            entities.forEach(this::setUpdateUser);
            return repository.saveAll(entities);
        }
    }

    public void delete(Long id) {
        E entity = findById(id);
        if (entity != null) {
            entity.setDeleted(true);
            update(entity);
        }
    }

    public void delete(List<Long> ids) {
        List<E> entities = findByIdIn(ids);
        if (entities.size() != 0) {
            entities.forEach(entity -> entity.setDeleted(true));
            update(entities);
        }
    }

    private void setCreateUser(E entity) {
        Long updateUserId = getCurrentUser() == null ? null : getCurrentUser().getUser().getId();
        entity.setCreatedByUserId(updateUserId);
        entity.setUpdatedByUserId(updateUserId);
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
    }

    private void setUpdateUser(E entity) {
        Long updateUserId = getCurrentUser() == null ? null : getCurrentUser().getUser().getId();
        entity.setUpdatedByUserId(updateUserId);
        entity.setUpdatedTime(new Date());
    }

    //------------------------------------------------------------------------------------------------------------------
    @Autowired
    private HospitalService hospitalService;

    @Value("${authorize.enable}")
    private boolean enableAuthorize;

    /**
     * Throw {@link UnAuthorizeException} if not {@link Role#SYSTEM_ADMIN}
     */
    public void checkUserAuthorize() {
        checkUserAuthorize(null, null, null);
    }

    /**
     * Throw {@link UnAuthorizeException} if
     * - not {@link Role#SYSTEM_ADMIN}
     * - or an manager of the hospital
     *
     * @param hospital the hospital need to check authorize of current user on it
     */
    public void checkUserAuthorize(Hospital hospital) {
        checkUserAuthorize(hospital, null, null);
    }

    /**
     * Throw {@link UnAuthorizeException} if
     * - not {@link Role#SYSTEM_ADMIN}
     * - or a manager of the hospital of the department
     * - or a manager of the department
     *
     * @param department the department need to check authorize of current user on it
     */
    public void checkUserAuthorize(Department department) {
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department);
    }

    /**
     * Throw {@link UnAuthorizeException} if
     * - not {@link Role#SYSTEM_ADMIN}
     * - or a manager of the hospital of the department
     * - or a manager of the department
     *
     * @param hospital   the hospital contain the department
     * @param department the department need to check authorize of current user on it
     *                   <p>
     *                   use this methor instead of {@link #checkUserAuthorize(Department)} to avoid duplicate query to database
     */
    public void checkUserAuthorize(Hospital hospital, Department department) {
        checkUserAuthorize(hospital, department, null);
    }


    /**
     * Throw {@link UnAuthorizeException} if
     * - not {@link Role#SYSTEM_ADMIN}
     * - or a manager of the hospital of the department contain the doctor
     * - or a manager of the department contain the doctor
     * - or the doctor itself
     *
     * @param department the department contain the doctor
     * @param doctor     the doctor need to check authorize of current user on it
     *                   <p>
     *                   use this methor instead of {@link #checkUserAuthorize(Department)} to avoid duplicate query to database
     */
    public void checkUserAuthorize(Department department, Doctor doctor) {
        Hospital hospital = hospitalService.findById(department.getHospitalId());
        checkUserAuthorize(hospital, department, doctor);
    }

    private void checkUserAuthorize(Hospital hospital, Department department, Doctor doctor) {
        if (!enableAuthorize) return;

        if (getCurrentUser().hasRole(Role.SYSTEM_ADMIN)) return;

        CustomUserDetails userAuthenticate = getCurrentUser();
        Long userId = userAuthenticate.getUser().getId();

        if (hospital != null && hospital.manageBy(userId)) return;

        if (department != null && department.manageBy(userId)) return;

        if (doctor != null && doctor.getUser().getId().equals(userId)) return;

        throw new UnAuthorizeException();
    }
}
