package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.service.schedule.AppointmentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentApi extends BaseResource<AppointmentService> {
}
