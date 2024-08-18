package com.eazybytes.accounts.service;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;

import jakarta.transaction.Transactional;

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

    public List<Accounts> getAccountsByIds(List<Long> idList) {
        List<Accounts> accountsList = new ArrayList<Accounts>();
        for (Long id : idList) {
            if (!accountsRepository.existsById(id)) {
                accountsList.add(null);//throw new RuntimeException("Account not found:" + id);
            }else{
                accountsList.add(accountsRepository.findById(id).get());
            }            
        }
        return accountsList;
    }

    public List<Accounts> findAllByIds(List<Long> accountIds) {
        return (List<Accounts>) accountsRepository.findAllById(accountIds);
    }

    public String save(Accounts accounts){
        int id = accounts.getCustomerId();
        if (customerRepository.existsById(id)) {
            // accounts.setCreateDt(LocalDate.now());
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
            //updateAccounts.setAccountNumber(id);
            // updateAccounts.setCreateDt(LocalDate.now());
            accountsRepository.save(updateAccounts);
            return "update successful!";
        }else {
            return "account not found!";
        }
    }

    public List<String> updateMultipleAccounts(List<Accounts> accountsList) {
        List<String> responseList = new ArrayList<String>();
        for (Accounts accounts : accountsList) {
            accountsRepository.save(accounts);
            responseList.add("Compte mise à jour avec succès : " + accounts.getAccountNumber());
        }
        return responseList;
    }
    
    @Transactional
    public List<String> saveAll(List<Accounts> accountsList) {
        List<String> responseList = new ArrayList<String>();
        List<Integer> customeList = new ArrayList<Integer>();
        for (Accounts accounts : accountsList) {
            customeList.add(accounts.getCustomerId());
        }
        int temp = customeList.get(0);
        boolean notEqual = false;
        for (Integer integer : customeList) {
            if (temp != integer){
                notEqual = true;
                break;
            }
        }
        if (notEqual){
            responseList.add("Les comptes créés en lot n'appartiennent pas au même client!" );
            return responseList;
        }

        for (Accounts accounts : accountsList) {
            try {
                accountsRepository.save(accounts);
                responseList.add("Compte enregistré avec succès : " + accounts.getAccountNumber());
                
            } catch (Exception e) {
                responseList.add("Échec d'enregistrement pour le compte : " + accounts.getAccountNumber() + " - Erreur : " + e.getMessage());
            }
        }
        return responseList;
    }

    public String deleteAllAccounts() {
        try {
            accountsRepository.deleteAll();
            return "Tous les comptes ont été supprimés avec succès";
        } catch (Exception e) {
            return "Erreur lors de la suppression des comptes : " + e.getMessage();
        }
    }

    public String deleteAccounts(List<Long> idList) {
        String results="";
        for (Long id : idList) {
            accountsRepository.deleteById(id);
            results += "Success delete:"+ id+"\n";
        }

        return results;
    }


}


