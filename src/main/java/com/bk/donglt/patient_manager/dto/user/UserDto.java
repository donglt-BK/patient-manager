package com.bk.donglt.patient_manager.dto.user;

import com.bk.donglt.patient_manager.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
    }
}
