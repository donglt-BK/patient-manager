package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.record.TreatmentDto;
import com.bk.donglt.patient_manager.entity.record.Conclusion;
import com.bk.donglt.patient_manager.entity.record.Treatment;
import com.bk.donglt.patient_manager.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TreatmentService extends BaseService<Treatment, TreatmentRepository> {
    void createTreatment(Conclusion conclusion, List<TreatmentDto> treatmentDto) {
        List<Treatment> treatments = treatmentDto.stream().map(
                dto -> new Treatment(conclusion, dto)
        ).collect(Collectors.toList());
        save(treatments);
    }

    void updateTreatment(Conclusion conclusion, List<TreatmentDto> treatmentDto) {
        createTreatment(conclusion, treatmentDto.stream().filter(dto -> dto.getId() == null).collect(Collectors.toList()));

        Map<Long, TreatmentDto> updateData = treatmentDto.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(TreatmentDto::getId, s -> s));
        List<Treatment> treatments = findByIdIn(updateData.keySet());
        for (Treatment treatment : treatments) {
            TreatmentDto newData = updateData.get(treatment.getId());
            treatment.setType(newData.getType());
            treatment.setDescription(newData.getDescription());
            treatment.setFrom(newData.getFrom());
            treatment.setTo(newData.getTo());
        }
        update(treatments);
    }
}
