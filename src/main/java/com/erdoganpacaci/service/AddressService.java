package com.erdoganpacaci.service;

import com.erdoganpacaci.dto.DtoAddress;
import com.erdoganpacaci.dto.DtoAddressUI;
import com.erdoganpacaci.exception.BaseException;

import java.util.List;

public interface AddressService {


    public DtoAddress saveAddress(DtoAddressUI dtoAddressUI) ;

    public DtoAddress updateAddress(Long id,DtoAddressUI dtoAddressUI);

    public Void deleteAddress(Long id);

    public List<DtoAddress> recordAllAddress(List<DtoAddressUI> dtoAddressUI);

    public DtoAddress getTheAddress(Long id);
}
