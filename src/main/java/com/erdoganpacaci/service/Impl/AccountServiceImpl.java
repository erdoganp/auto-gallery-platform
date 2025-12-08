package com.erdoganpacaci.service.Impl;

import com.erdoganpacaci.dto.DtoAccount;
import com.erdoganpacaci.dto.DtoAccountUI;
import com.erdoganpacaci.exception.BaseException;
import com.erdoganpacaci.exception.ErrorMessage;
import com.erdoganpacaci.exception.MessageType;
import com.erdoganpacaci.model.Account;
import com.erdoganpacaci.model.Customer;
import com.erdoganpacaci.repository.AccountRepository;
import com.erdoganpacaci.repository.CustomerRepository;
import com.erdoganpacaci.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Account createAccount(DtoAccountUI dtoAccountUI){

        Account account = new Account();
        account.setCreateTime(new Date());

        BeanUtils.copyProperties(dtoAccountUI, account);
        return account;

    }

    @Override
    public DtoAccount saveAccount(DtoAccountUI dtoAccountUI){
        DtoAccount dtoAccount = new DtoAccount();
        Account savedAccount=accountRepository.save(createAccount(dtoAccountUI));

        BeanUtils.copyProperties(savedAccount, dtoAccount);
        return dtoAccount;
    }

    @Override
    public List<DtoAccount> getAllAccounts(){


        List<DtoAccount> dtoAccounts=new ArrayList<>();
        List<Account> accounts=accountRepository.findAll();

        for(Account account:accounts){
            DtoAccount dtoAccount=new DtoAccount();
            BeanUtils.copyProperties(account, dtoAccount);
            dtoAccounts.add(dtoAccount);
        }

        return dtoAccounts;
    }

    @Override
    public DtoAccount updateAccount(Long accountNo, DtoAccountUI dtoAccountUI) {

        DtoAccount dtoAccount=new DtoAccount();

        Optional<Account> optAccount=accountRepository.findAccountByAccountNo(accountNo);
        if(optAccount.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, accountNo.toString()));

        }

        Account account=optAccount.get();
        BeanUtils.copyProperties(dtoAccountUI, account);


        Account dbAccount=accountRepository.save(account);
        BeanUtils.copyProperties(dbAccount, dtoAccount);
        return dtoAccount;
    }

    @Override
    public Void deleteAccount(Long accountId) {

        Optional<Account> optAccount=accountRepository.findById(accountId);
        if(optAccount.isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, accountId.toString()));

        }

        Optional<Customer> optCustomer =customerRepository.findWithAccount(accountId);
        if(optCustomer.isPresent()){
            throw new BaseException(new ErrorMessage(MessageType.DATA_IS_ALREADY_USED, accountId.toString()));

        }

        accountRepository.delete(optAccount.get());

        return null;
    }
}
