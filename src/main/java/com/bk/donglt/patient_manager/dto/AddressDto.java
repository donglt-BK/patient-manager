package com.bk.donglt.patient_manager.dto;

import com.bk.donglt.patient_manager.entity.address.Address;
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
        country = address.getCountry().getName();
        countryId = address.getCountry().getId();
        city = address.getCity().getName();
        cityId = address.getCity().getId();
        district = address.getDistrict().getName();
        districtId = address.getDistrict().getId();
        block = address.getBlock().getName();
        blockId = address.getBlock().getId();
        specificAddress = address.getSpecificAddress();
    }
}
