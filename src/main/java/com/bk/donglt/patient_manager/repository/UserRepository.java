package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByUsername(String username);

    List<User> findByIdIn(List<Long> ids);

    List<User> findByIdInAndRoleIn(List<Long> ids, List<String> roles);

    Page<User> findByNameContainingAndRoleIn(String name, List<String> roles, Pageable pageable);

    Page<User> findByNameContaining(String name, Pageable pageable);

}
