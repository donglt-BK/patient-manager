package com.bk.donglt.patient_manager.service.address;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.*;
import com.bk.donglt.patient_manager.repository.address.BlockRepository;
import com.bk.donglt.patient_manager.repository.address.CityRepository;
import com.bk.donglt.patient_manager.repository.address.CountryRepository;
import com.bk.donglt.patient_manager.repository.address.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService extends BaseService<Block, BlockRepository> {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public List<Country> listCountry() {
        return countryRepository.findByIsDeletedFalse();
    }

    public List<City> listCity(Long countryId) {
        return cityRepository.findByCountry_Id(countryId);
    }

    public List<District> listDistrict(Long cityId) {
        return districtRepository.findByCity_Id(cityId);
    }

    public List<Block> listBlock(Long districtId) {
        return repository.findByDistrict_Id(districtId);
    }

    public Address build(AddressDto addressDto) {
        if (addressDto == null) return null;
        Block block = findById(addressDto.getBlockId());
        Address address = new Address();
        address.setBlock(block);
        address.setSpecificAddress(addressDto.getSpecificAddress());
        return address;
    }
}
