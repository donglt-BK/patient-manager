package com.bk.donglt.patient_manager.dto.department;

import com.bk.donglt.patient_manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDataDto {
    private Long id;
    private Long hospitalId;
    private String name;
    private Status status;

}
