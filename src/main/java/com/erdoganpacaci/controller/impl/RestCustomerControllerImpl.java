package com.erdoganpacaci.controller.impl;

import com.erdoganpacaci.controller.RestBaseController;
import com.erdoganpacaci.controller.RestCustomerController;
import com.erdoganpacaci.controller.RootEntity;
import com.erdoganpacaci.dto.DtoCustomer;
import com.erdoganpacaci.dto.DtoCustomerUI;
import com.erdoganpacaci.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements RestCustomerController {


    @Autowired
    private CustomerService customerService;



    @PostMapping("/save")
    @Override
    public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerUI dtoCustomerUI) {
        return ok(customerService.saveCustomer(dtoCustomerUI));
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();


    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<DtoCustomer> updateCustomer( @Valid @PathVariable(value = "id") Long id,
                                                  @RequestBody DtoCustomerUI dtoCustomerUI) {

      return  ok(customerService.updateCustomer(id, dtoCustomerUI));

    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<DtoCustomer> getTheCustomer(@Valid @PathVariable(value = "id") Long id) {
        return ok(customerService.getTheCustomer(id));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<List<DtoCustomer>> getAllCustomer() {
        return ok(customerService.getAllCustomer());
    }


}
