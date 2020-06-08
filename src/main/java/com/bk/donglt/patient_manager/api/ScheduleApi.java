package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleChangeRequest;
import com.bk.donglt.patient_manager.enums.Shift;
import com.bk.donglt.patient_manager.service.schedule.ScheduleChangeRequestService;
import com.bk.donglt.patient_manager.service.schedule.ScheduleService;
import com.bk.donglt.patient_manager.util.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleApi extends BaseResource<ScheduleService> {
    @Autowired
    private ScheduleChangeRequestService changeRequestService;

    //---------------------------------Department schedule------------------------------------------------------------------------
    @GetMapping("/list")
    public ResponseEntity<List<Schedule>> list(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "departmentId") Long departmentId) {
        List<Schedule> schedules;
        schedules = service.findSchedules(
                departmentId,
                DateFormat.parseDate(start),
                DateFormat.parseDate(end)
        );
        return ResponseEntity.ok().body(schedules);
    }

    @PostMapping("/assign")
    public ResponseEntity<Schedule> assign(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "shift") Shift shift,
            @RequestParam(name = "doctorIds") String doctorIds) {

        return ResponseEntity.ok().body(
                service.assign(DateFormat.parseDate(date), shift, doctorIds.split("\\|\\|"))
        );
    }

    @PostMapping("/deAssign")
    public ResponseEntity<Schedule> deAssign(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "shift") Shift shift,
            @RequestParam(name = "doctorId") Long doctorId) {

        return ResponseEntity.ok().body(
                service.deAssign(DateFormat.parseDate(date), shift, doctorId)
        );
    }

    //---------------------------------Doctor schedule------------------------------------------------------------------------
    @GetMapping("/mySchedule")
    public ResponseEntity<List<Schedule>> mySchedule(@RequestParam(name = "departmentId") Long departmentId) {
        return ResponseEntity.ok().body(service.findMySchedule(departmentId));
    }

    @PostMapping("/requestChange")
    public ResponseEntity<ScheduleChangeRequest> change(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "shift") Shift shift,
            @RequestParam(name = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok().body(
                service.requestChange(DateFormat.parseDate(date), shift, scheduleId)
        );
    }

    //---------------------------------Schedule change request------------------------------------------------------------------------
    @GetMapping("/changeRequest/list")
    public ResponseEntity<Page<ScheduleChangeRequest>> changeRequestList(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(changeRequestService.findByDepartment(departmentId, getPageable(page, size)));
    }

    @GetMapping("/changeRequest/myChangeRequest")
    public ResponseEntity<Page<ScheduleChangeRequest>> myChangeRequestList(
            @RequestParam(name = "departmentId") Long departmentId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(changeRequestService.findMyChangeRequest(departmentId, getPageable(page, size)));
    }

    @PostMapping("/changeRequest/resolve")
    public ResponseEntity<Void> resolve(
            @RequestParam(name = "changeRequestId") Long changeRequestId,
            @RequestParam(name = "approve") boolean approve) {
        changeRequestService.resolve(changeRequestId, approve);
        return ResponseEntity.ok().build();
    }
}