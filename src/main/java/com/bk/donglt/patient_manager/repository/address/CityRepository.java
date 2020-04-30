package com.bk.donglt.patient_manager.repository.address;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.address.City;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends BaseRepository<City> {
    City findByName(String name);
    List<City> findByCountry_Id(long id);
}
