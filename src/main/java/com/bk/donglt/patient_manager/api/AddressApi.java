package com.bk.donglt.patient_manager.api;

import com.bk.donglt.patient_manager.base.BaseResource;
import com.bk.donglt.patient_manager.entity.address.Block;
import com.bk.donglt.patient_manager.entity.address.City;
import com.bk.donglt.patient_manager.entity.address.Country;
import com.bk.donglt.patient_manager.entity.address.District;
import com.bk.donglt.patient_manager.service.address.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressApi extends BaseResource<AddressService> {

    @GetMapping("/country")
    @ResponseBody
    public List<Country> country() {
        return service.listCountry();
    }

    @GetMapping("/city")
    @ResponseBody
    public List<City> city(@RequestParam Long countryId) {
        return service.listCity(countryId);
    }


    @GetMapping("/district")
    @ResponseBody
    public List<District> district(@RequestParam Long cityId) {
        return service.listDistrict(cityId);
    }

    @GetMapping("/block")
    @ResponseBody
    public List<Block> block(@RequestParam Long districtId) {
        return service.listBlock(districtId);
    }
}
