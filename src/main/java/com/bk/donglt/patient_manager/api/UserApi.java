package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.user.UserDataDto;
import com.bk.donglt.patient_manager.dto.user.UserDetailDto;
import com.bk.donglt.patient_manager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi extends BaseResource<UserService> {
    @GetMapping("/refresh")
    public ResponseEntity<UserDetailDto> refreshUserData() {
        UserDetailDto userInfo = service.getUserInfo();
        getCurrentUser().setUser(userInfo);
        return ResponseEntity.ok().body(userInfo);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDataDto data) {
        service.register(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UserDataDto dto) {
        service.update(dto);
        return ResponseEntity.ok().build();
    }
}