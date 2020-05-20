package com.bk.donglt.patient_manager.service;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.user.UserDataDto;
import com.bk.donglt.patient_manager.dto.user.UserDetailDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.enums.Role;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.DepartmentRepository;
import com.bk.donglt.patient_manager.repository.DoctorRepository;
import com.bk.donglt.patient_manager.repository.HospitalRepository;
import com.bk.donglt.patient_manager.repository.UserRepository;
import com.bk.donglt.patient_manager.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService extends BaseService<User, UserRepository> {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AddressService addressService;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public User findUser(String username) {
        return repository.findByUsernameAndIsDeletedFalse(username);
    }

    public List<User> findUsers(List<Long> ids) {
        if (ids.size() == 0) return new LinkedList<>();
        return repository.findByIdIn(ids);
    }

    public void register(UserDataDto userDto) {
        if (userDto.getId() != null) throw new BadRequestException("id MUST be null to create new account");
        User existUser = repository.findByUsernameAndIsDeletedFalse(userDto.getUsername());
        if (existUser != null) throw new BadRequestException("username exist");

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setFormattedAddress(addressService.build(userDto.getAddress()));
        userDto.setFormattedWorkAddress(addressService.build(userDto.getWorkAddress()));

        save(new User(userDto));
    }

    public void update(UserDataDto userDto) {
        if (userDto.getId() == null) throw new BadRequestException("id required");
        if (userDto.getPassword() != null) throw new BadRequestException("can NOT update password using thí API");

        userDto.setFormattedAddress(addressService.build(userDto.getAddress()));
        userDto.setFormattedWorkAddress(addressService.build(userDto.getWorkAddress()));

        save(new User(userDto));
    }

    public UserDetailDto getUserInfo() {
        return getUserInfo(getCurrentUser().getUser().getId());
    }

    public UserDetailDto getUserInfo(Long id) {
        User user = findById(id);
        UserDetailDto userDetailDto = new UserDetailDto(user);

        userDetailDto.setManageHospitalIds(hospitalRepository.findManageHospitalIdsByUser(id));
        userDetailDto.setManageDepartmentIds(departmentRepository.findManageDepartmentIdsByUser(id));
        userDetailDto.setDoctorIds(doctorRepository.findDoctorIdsByUserId(id));
        userDetailDto.setSystemAdmin(user.getRole().equals(Role.SYSTEM_ADMIN));
        return userDetailDto;
    }
}
