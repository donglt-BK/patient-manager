package com.bk.donglt.patient_manager.dto.manage;

import com.bk.donglt.patient_manager.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ManagerChangeDto {
    private Long hospitalId;
    private Long departmentId;

    private List<Long> addedManagerIds;
    private List<Long> removedManagerIds;

    private List<User> addedManagers;
}
