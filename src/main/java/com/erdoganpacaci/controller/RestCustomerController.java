package com.erdoganpacaci.controller;

import com.erdoganpacaci.dto.DtoCustomer;
import com.erdoganpacaci.dto.DtoCustomerUI;
import org.springframework.http.ResponseEntity;

public interface RestCustomerController {


    public RootEntity<DtoCustomer> saveCustomer(DtoCustomerUI dtoCustomerUI);
    public ResponseEntity<Void> deleteCustomer(Long id);
    public RootEntity<DtoCustomer> updateCustomer(Long id, DtoCustomerUI dtoCustomerUI);
    public RootEntity<DtoCustomer> getTheCustomer(Long id);

}
