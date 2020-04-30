package com.bk.donglt.patient_manager.base;

import com.bk.donglt.patient_manager.config.sercurity.CurrentUserDetailsContainer;
import com.bk.donglt.patient_manager.config.sercurity.CustomUserDetails;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.exception.UnAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;

public class BaseResource<S extends BaseService> {
    @Autowired
    protected S service;

    @Autowired
    private CurrentUserDetailsContainer userDetailsContainer;

    public CustomUserDetails getCurrentUser(){
        return this.userDetailsContainer.getUserDetails();
    }

    public Pageable getPageable(@RequestParam(name = "page", required = false, defaultValue = "1") int page, @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return PageRequest.of(page - 1, size);
    }

    @ExceptionHandler
    public ResponseEntity<Object> globalExceptionHandler(Exception e) {
        if (e instanceof UnAuthorizeException) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if (e instanceof BadRequestException) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
