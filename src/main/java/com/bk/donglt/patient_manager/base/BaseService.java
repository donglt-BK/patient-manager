package com.bk.donglt.patient_manager.base;

import com.bk.donglt.patient_manager.config.sercurity.CurrentUserDetailsContainer;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
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

        preSave(entity);

        return repository.save(entity);
    }

    public List<E> saveList(List<E> entities) {
        entities.forEach(this::preSave);
        return repository.saveAll(entities);
    }

    public void preSave(E entity) {
        entity.setCreatedTime(new Date());
        entity.setUpdatedTime(new Date());
    }

    public E findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public E update(E entity) {
        if (entity == null || entity.getId() == null) {
            return null;
        } else {
            entity.setUpdatedTime(new Date());
            return repository.save(entity);
        }
    }

    public boolean delete(Long id) {
        E entity = repository.findById(id).orElse(null);
        if (entity == null) {
            return false;
        } else {
            repository.delete(entity);
            return true;
        }
    }

}
