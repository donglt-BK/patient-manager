package com.bk.donglt.patient_manager.dto.record;

import com.bk.donglt.patient_manager.dto.doctor.DoctorDto;
import com.bk.donglt.patient_manager.dto.user.UserDto;
import com.bk.donglt.patient_manager.entity.record.Record;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecordDto {
    private Long id;
    private Long appointmentId;
    private UserDto patient;
    private DoctorDto doctor;
    private List<SymptomDto> symptoms;
    private List<ConclusionDto> conclusions;

    public RecordDto(Record record) {
        id = record.getId();
        appointmentId = record.getAppointment().getId();

    }
}
