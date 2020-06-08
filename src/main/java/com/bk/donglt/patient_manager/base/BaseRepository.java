package com.bk.donglt.patient_manager.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends JpaRepository<E, Long> {
    Page<E> findByIsDeletedFalse(Pageable pageable);

    List<E> findByIdIn(Collection<Long> ids);

}
