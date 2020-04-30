package com.bk.donglt.patient_manager.base;

import com.bk.donglt.patient_manager.config.sercurity.CurrentUserDetailsContainer;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public E update(E entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        } else {
            setUpdateUser(entity);
            return repository.save(entity);
        }
    }

    public boolean delete(Long id) {
        E entity = findById(id);
        if (entity == null) {
            return false;
        } else {
            setUpdateUser(entity);
            entity.setDeleted(true);
            update(entity);
            return true;
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
}
