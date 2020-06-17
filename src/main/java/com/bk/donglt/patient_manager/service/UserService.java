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
import com.bk.donglt.patient_manager.service.schedule.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private RecordService recordService;

    public User findUser(String username) {
        return repository.findByUsernameAndIsDeletedFalse(username);
    }

    public void register(UserDataDto userDto) {
        User existUser = repository.findByUsernameAndIsDeletedFalse(userDto.getUsername());
        if (existUser != null) throw new BadRequestException("username exist");

        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setFormattedAddress(addressService.build(userDto.getAddress()));
        userDto.setAvatar("default-avatar-2.jpg");
        save(new User(userDto));
    }

    public void update(UserDataDto userDto) {
        userDto.setFormattedAddress(addressService.build(userDto.getAddress()));

        User user = findById(getCurrentUser().getUser().getId());

        user.setName(userDto.getName());
        user.setDob(userDto.getDob());
        user.setAvatar(userDto.getAvatar());
        user.setGender(userDto.getGender());
        user.setAddress(userDto.getFormattedAddress());
        user.setPhone(userDto.getPhone());
        user.setEmail(userDto.getEmail());

        getCurrentUser().updateUser(userDto);
        update(user);
    }

    public UserDetailDto getUserInfo() {
        return getUserInfo(getCurrentUser().getUser().getId());
    }

    public UserDetailDto getUserInfo(Long id) {
        User user = findById(id);
        UserDetailDto userDetailDto = new UserDetailDto(user);

        userDetailDto.setManageHospitalIds(hospitalRepository.findManageHospitalIdsByUser(id));
        userDetailDto.setManageDepartmentIds(departmentRepository.findManageDepartmentIdsByUser(id));

        userDetailDto.setManageDepartmentHospitalId(departmentRepository.findManageDepartmentHospitalIdsByUser(id));
        userDetailDto.setDoctorIds(doctorRepository.findDoctorIdsByUserId(id));
        userDetailDto.setSystemAdmin(user.getRole().equals(Role.SYSTEM_ADMIN));

        //userDetailDto.setDoctorRequest(recordService.getRequest(id));
        return userDetailDto;
    }

    public Page<User> find(Pageable pageable, String key) {
        if ("".equals(key)) return repository.findWithoutKey(pageable);
        return repository.findByKey(key, pageable);
    }
}
