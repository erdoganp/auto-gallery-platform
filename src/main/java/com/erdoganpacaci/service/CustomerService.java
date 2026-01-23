package com.erdoganpacaci.service;

import com.erdoganpacaci.dto.DtoAccountUI;
import com.erdoganpacaci.dto.DtoCustomer;
import com.erdoganpacaci.dto.DtoCustomerUI;

import java.util.List;

public interface CustomerService {

    public DtoCustomer saveCustomer(DtoCustomerUI dtoCustomerUI);

    public DtoCustomer updateCustomer(Long id, DtoCustomerUI dtoCustomerUI);

    public Void deleteCustomer(Long id);

    public DtoCustomer getTheCustomer(Long id);

    public List<DtoCustomer> getAllCustomer();

}
