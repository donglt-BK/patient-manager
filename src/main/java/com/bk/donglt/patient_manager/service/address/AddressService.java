package com.bk.donglt.patient_manager.service.address;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.entity.address.Block;
import com.bk.donglt.patient_manager.repository.address.BlockRepository;
import com.bk.donglt.patient_manager.repository.address.CityRepository;
import com.bk.donglt.patient_manager.repository.address.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<Block, BlockRepository> {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private DistrictRepository districtRepository;

    public Address build(AddressDto addressDto) {
        if (addressDto == null) return null;
        Block block = findById(addressDto.getBlockId());
        Address address = new Address();
        address.setBlock(block);
        address.setSpecificAddress(addressDto.getSpecificAddress());
        return address;
    }
}
