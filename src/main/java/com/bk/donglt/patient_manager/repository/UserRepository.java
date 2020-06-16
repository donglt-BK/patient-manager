package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<User> {
    User findByUsernameAndIsDeletedFalse(String username);

    List<User> findByIdInAndRoleIn(List<Long> ids, List<String> roles);

    @Query(value = "select u from User u where u.isDeleted = false and (u.name like %?1% or u.username like %?1% or CONCAT(u.id, '') like %?1%) order by u.updatedTime desc, u.id")
    Page<User> findByKey(String key, Pageable pageable);

}
