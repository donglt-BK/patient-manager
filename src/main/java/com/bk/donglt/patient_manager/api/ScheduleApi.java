package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.enums.Shift;
import com.bk.donglt.patient_manager.service.schedule.ScheduleService;
import com.bk.donglt.patient_manager.util.DateFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleApi extends BaseResource<ScheduleService> {
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
    @GetMapping("/listBook")
    public ResponseEntity<List<Schedule>> listBook(
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end,
            @RequestParam(name = "departmentId") Long departmentId) {
        List<Schedule> schedules;
        schedules = service.findBookSchedules(
                departmentId,
                DateFormat.parseDate(start),
                DateFormat.parseDate(end)
        );
        return ResponseEntity.ok().body(schedules);
    }
    @PostMapping("/toggle")
    public ResponseEntity<Void> toggle(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "shift") Shift shift,
            @RequestParam(name = "isClosed") boolean isClosed,
            @RequestParam(name = "departmentId") Long departmentId) {
        service.toggle(DateFormat.parseDate(date), shift, isClosed, departmentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Schedule> create(
            @RequestParam(name = "date") String date,
            @RequestParam(name = "shift") Shift shift,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "isClosed") boolean isClosed,
            @RequestParam(name = "departmentId") Long departmentId) {

        return ResponseEntity.ok().body(
                service.create(DateFormat.parseDate(date), shift,
                        limit, isClosed, departmentId)
        );
    }
}