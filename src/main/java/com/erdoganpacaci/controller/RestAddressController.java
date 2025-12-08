package com.erdoganpacaci.controller;

import com.erdoganpacaci.dto.DtoAddress;
import com.erdoganpacaci.dto.DtoAddressUI;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface RestAddressController {

    public RootEntity<DtoAddress> saveAddress(@Valid @RequestBody DtoAddressUI dtoAddressUI);

    public RootEntity<DtoAddress> updateAddress(@Valid @PathVariable @RequestBody Long id, DtoAddressUI dtoAddressUI);

    public RootEntity<Void> deleteAddress(@Valid @RequestBody Long id);

    public RootEntity<List<DtoAddress>> recordAllAddresses(@Valid @RequestBody List<DtoAddressUI> dtoAddressUI);

    public RootEntity<DtoAddress> getTheAddress(@Valid @PathVariable Long id);
}
