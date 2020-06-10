package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.record.SymptomDto;
import com.bk.donglt.patient_manager.entity.record.Record;
import com.bk.donglt.patient_manager.entity.record.Symptom;
import com.bk.donglt.patient_manager.repository.SymptomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SymptomService extends BaseService<Symptom, SymptomRepository> {
    void createSymptom(Record record, List<SymptomDto> symptomDto) {
        List<Symptom> symptoms = symptomDto.stream().map(
                dto -> new Symptom(record, dto)
        ).collect(Collectors.toList());
        save(symptoms);
    }

    void updateSymptom(Record record, List<SymptomDto> symptomDto) {
        createSymptom(
                record,
                symptomDto.stream()
                        .filter(dto -> dto.getId() == null)
                        .collect(Collectors.toList())
        );

        Map<Long, SymptomDto> updateData = symptomDto.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(SymptomDto::getId, s -> s));
        List<Symptom> symptoms = findByIdIn(updateData.keySet());
        for (Symptom symptom : symptoms) {
            SymptomDto newData = updateData.get(symptom.getId());
            symptom.setType(newData.getType());
            symptom.setDescription(newData.getDescription());
        }
        update(symptoms);
    }
}
