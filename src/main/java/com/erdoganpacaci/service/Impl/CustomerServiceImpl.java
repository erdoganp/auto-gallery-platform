package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.*;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Account;
import com.erdoganpacaci.model.Address;
import com.erdoganpacaci.model.Customer;
import com.erdoganpacaci.repository.AccountRepository;
import com.erdoganpacaci.repository.AddressRepository;
import com.erdoganpacaci.repository.CustomerRepository;
import com.erdoganpacaci.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private AddressRepository addressRepository;


    @Autowired
    private AccountRepository accountRepository;

    private Customer createCustomer (DtoCustomerUI dtoCustomerUI) {

        Optional<Address>optionalAddress = addressRepository.findById(dtoCustomerUI.getAddressId());

        Optional<Customer> optionalCustomerforAddress=customerRepository.findWithAddress(dtoCustomerUI.getAddressId());
        Optional<Customer> optionalCustomerforAccount=customerRepository.findWithAccount(dtoCustomerUI.getAccountId());

        if(optionalAddress.isEmpty()){;
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerUI.getAddressId().toString()));

        }

       Optional<Account> optAccount= accountRepository.findById(dtoCustomerUI.getAccountId());

        if(optAccount.isEmpty()){;
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCustomerUI.getAccountId().toString()));

        }

        if(!optionalCustomerforAccount.isEmpty() ){
            throw new BaseException(new ErrorMessage(MessageType.DATA_IS_ALREADY_USED, "AccountId :"+dtoCustomerUI.getAccountId().toString()));

        }

        if( !optionalCustomerforAddress.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.DATA_IS_ALREADY_USED, "Address Id :" + dtoCustomerUI.getAddressId().toString()));

        }
        Customer customer = new Customer();
        customer.setCreateTime((new Date()));

        BeanUtils.copyProperties(dtoCustomerUI, customer);

        customer.setAddress(optionalAddress.get());
        customer.setAccount(optAccount.get());

        return customer;


    }


    @Override
    public DtoCustomer saveCustomer(DtoCustomerUI dtoCustomerUI) {
        DtoCustomer dtoCustomer = new DtoCustomer();
        DtoAddress dtoAddress = new DtoAddress();
        DtoAccount dtoAccount = new DtoAccount();

        Customer savedCustomer=customerRepository.save(createCustomer(dtoCustomerUI));

        BeanUtils.copyProperties(savedCustomer, dtoCustomer);
        BeanUtils.copyProperties(savedCustomer.getAddress(), dtoAddress);
        BeanUtils.copyProperties(savedCustomer.getAccount(), dtoAccount);

        dtoCustomer.setAddress(dtoAddress);
        dtoCustomer.setAccount(dtoAccount);

        return dtoCustomer;
    }

    @Override
    public DtoCustomer updateCustomer(Long id, DtoCustomerUI dtoCustomerUI) {

        DtoCustomer dtoCustomer = new DtoCustomer();
        DtoAddress dtoaddress = new DtoAddress();
        DtoAccount dtoaccount = new DtoAccount();

        Optional<Customer> optionalCustomer=customerRepository.findById(id);
         if(optionalCustomer.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

         }




         Customer dbCustomer=optionalCustomer.get();
         Address dbaddress=optionalCustomer.get().getAddress();
         Account dbaccount=optionalCustomer.get().getAccount();

         BeanUtils.copyProperties(dtoCustomerUI, dbCustomer);
         BeanUtils.copyProperties(dbaddress, dtoaddress);
         BeanUtils.copyProperties(dbaccount, dtoaccount);

         dbCustomer.setCreateTime((new Date()));
         dbCustomer.setAddress(dbaddress);
         dbCustomer.setAccount(dbaccount);

       Customer savedCustomer=  customerRepository.save(dbCustomer);



       BeanUtils.copyProperties(savedCustomer, dtoCustomer);
       dtoCustomer.setAddress(dtoaddress);
       dtoCustomer.setAccount(dtoaccount);
       return dtoCustomer;

    }

    @Override
    public Void deleteCustomer(Long id) {

        Optional<Customer> optCustomer=customerRepository.findById(id);
        if(!optCustomer.isPresent()){
           throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));

        }
        customerRepository.delete(optCustomer.get());

        return null;
    }


}
