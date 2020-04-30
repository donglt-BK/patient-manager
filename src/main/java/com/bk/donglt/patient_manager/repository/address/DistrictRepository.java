package com.bk.donglt.patient_manager.repository.address;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.address.District;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends BaseRepository<District> {
    District findByName(String name);
    List<District> findByCity_Id(long id);
}
