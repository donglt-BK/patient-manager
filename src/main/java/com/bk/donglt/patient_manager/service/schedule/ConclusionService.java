package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.record.ConclusionDto;
import com.bk.donglt.patient_manager.entity.record.Conclusion;
import com.bk.donglt.patient_manager.entity.record.Record;
import com.bk.donglt.patient_manager.repository.ConclusionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConclusionService extends BaseService<Conclusion, ConclusionRepository> {
    @Autowired
    private TreatmentService treatmentService;

    void createConclusion(Record record, List<ConclusionDto> conclusionDto) {
        List<Conclusion> conclusions = conclusionDto.stream().map(dto -> {
            Conclusion conclusion = new Conclusion(record, dto);
            treatmentService.createTreatment(conclusion, dto.getTreatments());
            return conclusion;
        }).collect(Collectors.toList());
        save(conclusions);
    }

    void updateConclusion(Record record, List<ConclusionDto> conclusionDto) {
        createConclusion(record, conclusionDto.stream().filter(dto -> dto.getId() == null).collect(Collectors.toList()));

        Map<Long, ConclusionDto> updateData = conclusionDto.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(ConclusionDto::getId, s -> s));
        List<Conclusion> conclusions = findByIdIn(updateData.keySet());
        for (Conclusion conclusion : conclusions) {
            ConclusionDto newData = updateData.get(conclusion.getId());
            conclusion.setConclusion(newData.getConclusion());

            treatmentService.updateTreatment(conclusion, newData.getTreatments());
            treatmentService.delete(newData.getRemoveTreatments());
        }
        update(conclusions);
    }
}
