package com.bk.donglt.patient_manager.service.schedule;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.record.RecordDataDto;
import com.bk.donglt.patient_manager.entity.Appointment;
import com.bk.donglt.patient_manager.entity.Doctor;
import com.bk.donglt.patient_manager.entity.record.Record;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.RecordRepository;
import com.bk.donglt.patient_manager.service.manager.DepartmentService;
import com.bk.donglt.patient_manager.service.manager.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecordService extends BaseService<Record, RecordRepository> {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private ConclusionService conclusionService;

    /*private final Map<String, Date> recordPermission = new HashMap<>();
    private final Map<Long, Set<Long>> permissionRequest = new HashMap<>();

    private boolean havePermission(long patientId, long doctorId) {
        String key = patientId + "||" + doctorId;
        boolean havePermission = false;
        if (recordPermission.containsKey(key)) {
            Date deadline = recordPermission.get(key);
            if (deadline.after(new Date())) {
                havePermission = true;
            }
        } else {
            Set<Long> requestDoctors = permissionRequest.getOrDefault(patientId, new HashSet<>());
            requestDoctors.add(doctorId);
            permissionRequest.put(patientId, requestDoctors);
            //TODO send socket notify to patient
        }
        return havePermission;
    }

    public List<DoctorDto> getRequest(Long userId) {
        Set<Long> requestedDoctorIds = permissionRequest.getOrDefault(userId, new HashSet<>());
        List<Doctor> doctors = doctorService.findByIdIn(requestedDoctorIds);
        Map<Long, Department> departmentMap =
                departmentService.findByIdIn(
                        doctors.stream()
                                .map(Doctor::getDepartmentId)
                                .collect(Collectors.toList())
                ).stream().collect(Collectors.toMap(
                        Department::getId,
                        department -> department
                ));
        return doctors.stream()
                .map(doctor -> new DoctorDto(departmentMap.get(doctor.getDepartmentId()), doctor))
                .collect(Collectors.toList());
    }

    public void grantPermission(long doctorId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 3);
        Long userId = getCurrentUser().getUser().getId();
        String key = userId + "||" + doctorId;
        recordPermission.put(key, calendar.getTime());

        if (permissionRequest.containsKey(userId)) {
            Set<Long> userRequest = permissionRequest.get(userId);
            userRequest.remove(doctorId);
            permissionRequest.put(userId, userRequest);
        }
        //TODO send socket notify to doctor
    }*/

    public Page<Record> findMyRecord(Pageable pageable) {
        Long userId = getCurrentUser().getUser().getId();
        return repository.findByPatientIdAndIsDeletedFalse(userId, pageable);
    }

    public Page<Record> findPatientRecord(long patientId, long departmentId, Pageable pageable) {
        Doctor doctor = doctorService.findMeInDepartment(departmentId);
        /*if (!havePermission(patientId, doctor.getId()))
            throw new BadRequestException("Doctor need permission for this record");
        */
        return repository.findByPatientIdAndIsDeletedFalse(patientId, pageable);
    }

    public Record create(RecordDataDto data) {
        Record record = new Record();

        if (data.getAppointmentId() != null) {
            Appointment appointment = appointmentService.findById(data.getAppointmentId());
            if (appointment.getRecord() != null) throw new BadRequestException("Appointment already have record");
            record.setAppointment(appointment);
        }

        record.setPatientId(data.getPatientId());
        record.setDoctorId(data.getDoctorId());

        Record savedRecord = save(record);

        symptomService.createSymptom(record, data.getSymptoms());
        conclusionService.createConclusion(record, data.getConclusions());

        return savedRecord;
    }

    public Record update(RecordDataDto data) {
        Record record = findById(data.getId());

        symptomService.updateSymptom(record, data.getSymptoms());
        symptomService.delete(data.getRemoveSymptoms());

        conclusionService.updateConclusion(record, data.getConclusions());
        conclusionService.delete(data.getRemoveConclusions());

        return record;
    }


}
