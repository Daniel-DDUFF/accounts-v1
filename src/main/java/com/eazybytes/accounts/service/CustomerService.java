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
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountsRepository accountsRepository;

    public List<Customer> getAllCustomer(){
        List<Customer> customers= new ArrayList<Customer>();
        customerRepository.findAll().forEach(customer -> customers.add(customer));
        return customers;
    }

    public Customer getCustomerById(int id){
        return customerRepository.findById(id).get();
    }

    public void save(Customer customer){
        customer.setCreateDt(LocalDate.now());
        customerRepository.save(customer);
    }

    public String update(int id, Customer updateCustomer){
        Customer customerFind = customerRepository.findById(id).orElse(null);
        if (customerFind != null){
            updateCustomer.setCustomerId(id);
            updateCustomer.setCreateDt(LocalDate.now());
            //customerRepository.save() if customer exist, update,or create a new customer
            customerRepository.save(updateCustomer);
            return "update successful!";
        }else {
            return "customer not found";
        }
    }

    public String deleteCustomer(int id){
       Accounts account =  accountsRepository.findByCustomerId(id);
        if (account != null) {
            return "failed";
        } else {
            customerRepository.deleteById(id);
            return "Ok";
        }
    }
    public boolean customerExist(int id){
        return customerRepository.existsById(id);
    }
}
