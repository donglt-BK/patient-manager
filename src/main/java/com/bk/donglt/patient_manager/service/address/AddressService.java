package com.bk.donglt.patient_manager.service.address;

import com.bk.donglt.patient_manager.base.BaseService;
import com.bk.donglt.patient_manager.dto.AddressDto;
import com.bk.donglt.patient_manager.entity.address.Address;
import com.bk.donglt.patient_manager.entity.address.Block;
import com.bk.donglt.patient_manager.exception.BadRequestException;
import com.bk.donglt.patient_manager.repository.address.BlockRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<Block, BlockRepository> {
    public Address build(AddressDto addressDto) {
        Block block = repository.findById(addressDto.getBlockId()).orElseThrow(BadRequestException::new);
        if (block.getDistrict().getId() != addressDto.getDistrictId()
                || block.getDistrict().getCity().getId() != addressDto.getCityId()
                || block.getDistrict().getCity().getCountry().getId() != addressDto.getCountryId())
            throw new BadRequestException();
        Address address = new Address();
        address.setBlock(block);
        address.setDistrict(block.getDistrict());
        address.setCity(block.getDistrict().getCity());
        address.setCountry(block.getDistrict().getCity().getCountry());
        address.setSpecificAddress(addressDto.getSpecificAddress());
        return address;
    }
}
