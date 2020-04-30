package com.bk.donglt.patient_manager.repository.address;

import com.bk.donglt.patient_manager.base.BaseRepository;
import com.bk.donglt.patient_manager.entity.address.Country;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends BaseRepository<Country> {
    Country findByName(String name);
}
