package com.bk.donglt.patient_manager.service;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService extends BaseService<User, UserRepository> {
    public User findUser(String username) {
        return repository.findByUsername(username);
    }
    public List<User> findUsers(List<Long> ids) {
        if (ids.size() == 0) return new LinkedList<>();
        return repository.findByIdIn(ids);
    }
}
