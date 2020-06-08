package com.bk.donglt.patient_manager.dto.user;

import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailDto {
    private Long id;

    private String username;

    private String name;
    private Date dob;
    private Gender gender;

    private AddressDto address;
    private AddressDto workAddress;

    private String phone;
    private String email;

    private List<Long> manageHospitalIds;
    private List<Long> manageDepartmentIds;

    private List<Long> doctorIds;
    private List<DoctorDto> doctorRequest;

    private boolean isSystemAdmin;

    public UserDetailDto(User user) {
        id = user.getId();
        username = user.getUsername();
        name = user.getName();
        dob = user.getDob();
        gender = user.getGender();
        address = new AddressDto(user.getAddress());
        workAddress = new AddressDto(user.getWorkAddress());
        phone = user.getPhone();
        email = user.getEmail();
    }
}
