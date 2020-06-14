package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.entity.hospital.ScheduleStatus;
import com.bk.donglt.patient_manager.repository.ScheduleStatusRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleStatusService extends BaseService<ScheduleStatus, ScheduleStatusRepository> {
    public ScheduleStatus findByScheduleId(Long scheduleId) {
        return repository.findByScheduleId(scheduleId);
    }

    public void create(Long id) {
        ScheduleStatus scheduleStatus = new ScheduleStatus();
        scheduleStatus.setCurrentBook(0);
        scheduleStatus.setScheduleId(id);
        save(scheduleStatus);
    }

    public void increase(ScheduleStatus scheduleStatus) {
        scheduleStatus.increase();
        update(scheduleStatus);
    }


    public void decrease(ScheduleStatus scheduleStatus) {
        scheduleStatus.decrease();
        update(scheduleStatus);
    }

}
