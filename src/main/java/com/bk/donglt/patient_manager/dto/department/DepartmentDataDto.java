package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.entity.User;
import com.bk.donglt.patient_manager.entity.address.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDataDto {
    private Long id;
    private Long hospitalId;
    private String name;

    private List<Long> addedManagerIds;
    private List<Long> removedManagerIds;

    private List<User> addedManagers;
    private Address address;
}
