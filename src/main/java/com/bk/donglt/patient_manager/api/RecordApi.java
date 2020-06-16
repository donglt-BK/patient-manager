package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.dto.record.RecordDataDto;
import com.bk.donglt.patient_manager.entity.record.Record;
import com.bk.donglt.patient_manager.service.schedule.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class RecordApi extends BaseResource<RecordService> {
    @GetMapping("/myRecord")
    public ResponseEntity<Page<Record>> findMyRecord(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findMyRecord(getPageable(page, size)));
    }

    @GetMapping("/patient")
    public ResponseEntity<Page<Record>> findPatientRecord(
            @RequestParam("departmentId") long departmentId,
            @RequestParam("patientId") long patientId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.findPatientRecord(patientId, departmentId, getPageable(page, size)));
    }
/*
    @PostMapping("/grantPermission")
    public ResponseEntity<Void> grantPermission(@RequestParam("doctorId") long doctorId) {
        service.grantPermission(doctorId);
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/create")
    public ResponseEntity<Record> create(@RequestBody RecordDataDto dataDto) {
        return ResponseEntity.ok().body(service.create(dataDto));
    }

    @PostMapping("/update")
    public ResponseEntity<Record> update(@RequestBody RecordDataDto dataDto) {
        return ResponseEntity.ok().body(service.update(dataDto));
    }
}
