package com.bk.donglt.patient_manager.dto;

import com.bk.donglt.patient_manager.entity.address.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {
    private String country;
    private String city;
    private String district;
    private String block;

    private long countryId;
    private long cityId;
    private long districtId;
    private long blockId;

    private String specificAddress;

    public AddressDto(Address address) {
        if (address == null) return;
        Block block = address.getBlock();
        this.block = block.getName();
        blockId = block.getId();

        District district = block.getDistrict();
        this.district = district.getName();
        districtId = district.getId();

        City city = district.getCity();
        this.city = city.getName();
        cityId = city.getId();

        Country country = city.getCountry();
        this.country = country.getName();
        countryId = country.getId();
        
        specificAddress = address.getSpecificAddress();
    }
}
