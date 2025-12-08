package com.erdoganpacaci.controller.impl;

import com.erdoganpacaci.controller.RestAddressController;
import com.erdoganpacaci.controller.RestBaseController;
import com.erdoganpacaci.controller.RootEntity;
import com.erdoganpacaci.dto.DtoAddress;
import com.erdoganpacaci.dto.DtoAddressUI;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erdoganpacaci.controller.RootEntity.ok;

@RestController
@RequestMapping("/rest/api/address")
public class RestAddressControllerImpl extends RestBaseController implements RestAddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping("/save")
    @Override
    public RootEntity<DtoAddress> saveAddress(@Valid @RequestBody DtoAddressUI dtoAddressUI) {
        return ok(addressService.saveAddress(dtoAddressUI));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoAddress> updateAddress(@Valid @PathVariable(value = "id") Long id,
                                                @RequestBody DtoAddressUI dtoAddressUI) {
        return ok(addressService.updateAddress(id, dtoAddressUI));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RootEntity<Void> deleteAddress(@Valid @PathVariable(value = "id") Long id) {

        return ok(addressService.deleteAddress(id));
    }


    @PostMapping("/recordAllAddress")
    @Override
    public RootEntity<List<DtoAddress>> recordAllAddresses(@Valid @RequestBody List<DtoAddressUI> dtoAddressUI) {
        return ok(addressService.recordAllAddress(dtoAddressUI));
    }

    @GetMapping("/getTheAddress/{id}")
    @Override
    public RootEntity<DtoAddress> getTheAddress(@Valid @PathVariable(value = "id") Long id) {
        return ok(addressService.getTheAddress(id));

    }
}