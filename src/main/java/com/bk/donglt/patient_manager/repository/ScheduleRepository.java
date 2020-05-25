package com.bk.donglt.patient_manager.repository;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.hospital.Schedule;
import com.bk.donglt.patient_manager.enums.Shift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends BaseRepository<Schedule> {
    List<Schedule> findByDateBetweenAndDepartmentIdAndIsDeletedFalse(Date start, Date end, Long departmentId);

    Schedule findByDateAndShiftAndDepartmentIdAndIsDeletedFalse(Date date, Shift shift, Long departmentId);

    @Query(nativeQuery = true,
            value = "select s.* from schedule s left join doctor_schedule ds on s.id = ds.schedule_id where ds.doctor_id = ?1")
    List<Schedule> findByUserId(Long userId);
}
