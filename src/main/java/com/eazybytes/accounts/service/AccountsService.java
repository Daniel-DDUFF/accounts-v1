package com.eazybytes.accounts.service;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountsService {
    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    CustomerRepository customerRepository;

    public List<Accounts> getAllAccounts(){
        List<Accounts> allAccounts = new ArrayList<Accounts>();
        accountsRepository.findAll().forEach(accounts -> allAccounts.add(accounts));
        return allAccounts;
    }

    public Accounts getAccountsById(long id){
        return accountsRepository.findById(id).get();
    }

    public String save(Accounts accounts){
        int id = accounts.getCustomerId();
        if (customerRepository.existsById(id)) {
            accounts.setCreateDt(LocalDate.now());
            accountsRepository.save(accounts);
            return "saved";
        }else {
            return "failed,customer not found!";
        }
    }

    public String deleteAccount(Long id){
        accountsRepository.deleteById(id);
        return "deleted!";
    }

    public String updateAccount(long id, Accounts updateAccounts){
        Accounts accountsFind = accountsRepository.findById(id).orElse(null);
        if(accountsFind != null){
            updateAccounts.setAccountNumber(id);
            updateAccounts.setCreateDt(LocalDate.now());
            accountsRepository.save(updateAccounts);
            return "update successful!";
        }else {
            return "account not found!";
        }
    }
}
