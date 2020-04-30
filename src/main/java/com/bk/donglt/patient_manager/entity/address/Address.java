package com.bk.donglt.patient_manager.entity.address;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@Getter
@Setter
public class Address {
    @ManyToOne
    private Country country;

    @ManyToOne
    private City city;

    @ManyToOne
    private District district;

    @ManyToOne
    private Block block;

    private String specificAddress;

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Address)) {
            return false;
        }

        Address address = (Address) obj;
        return address.getBlock().equals(block)
                && address.getDistrict().equals(district)
                && address.getCity().equals(city)
                && address.getCountry().equals(country)
                && address.getSpecificAddress().equals(specificAddress);
    }
}