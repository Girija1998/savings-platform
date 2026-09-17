package com.saving.service.service;

import com.saving.service.dto.SavingRequest;
import com.saving.service.entity.SavingAccount;
import com.saving.service.entity.Transaction;
import com.saving.service.exception.DuplicateAccountException;
import com.saving.service.exception.ResourceNotFoundException;
import com.saving.service.repository.SavingRepository;
import com.saving.service.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingService {
    private static final Logger logger =
            LoggerFactory.getLogger(
                    SavingService.class
            );

    @Autowired
    private SavingRepository repository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public String createAccount(
            SavingRequest request
    )
    {
        logger.info(
                "Create account request received"
        );
        if (repository.existsByMobile(
                request.getMobile()
        ))
        {
            throw new DuplicateAccountException(
                    "Account already exist with this mobile number "
            );
        }
        SavingAccount account=new SavingAccount();

        account.setCustomerName(
                request.getCustomerName());

        account.setMobile(
                request.getMobile());

        account.setBalance(

                request.getInitialDeposit());

         repository.save(account);

         sendAccountCreatedEvent(account);
         return "Savings Account Created Successfully";
    }

    public SavingAccount getAccountById(Long id){

        return repository.findById(id)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Account not found with id : " + id
                        )
                );
    }

    public List<SavingAccount>
    getAllAccounts() {

        return repository.findAll();
    }
    public String updateAccount(
            Long id,
            SavingRequest request) {

        SavingAccount account =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account not found with id : " + id
                                )
                        );

        account.setCustomerName(
                request.getCustomerName());

        account.setMobile(
                request.getMobile());

        account.setBalance(

                request.getInitialDeposit());
        logger.info(
                "Saving account into database"
        );

        repository.save(account);

        return "Account Updated Successfully";
    }
    public String deleteAccount(Long id){
        repository.deleteById(id);

        return "Account deleted successfully!";
    }

    public SavingAccount getAccountByMobile(String mobile){
        return repository.findByMobile(mobile)
                .orElseThrow(
                        ()->new ResourceNotFoundException
                                ("Account not found with mobile: " + mobile)
                );
    }

    public List<SavingAccount> getAccountByCustomerName(String customerName){
        return repository.findByCustomerName(
                customerName
        );
    }
     public Page<SavingAccount> getAccountWithPagination(
             int page,
             int size
     ){
         Pageable pageable= PageRequest.of(page,size);

         return repository.findAll(pageable);
     }
     public List<SavingAccount> getAccountsSortedByName(){
        return repository.findAll(
                Sort.by("customerName")
        );
     }

     public String depositMoney(Long accountId, Double amount){
        SavingAccount account=repository.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Account not found with id : "+ accountId
                )
                );

        account.setBalance(
                account.getBalance()+amount
        );
        repository.save(account);

         Transaction transaction=new Transaction();

         transaction.setAccountId(accountId);
         transaction.setType("DEPOSIT");
         transaction.setAmount(amount);
         transaction.setBalance(account.getBalance());

         transactionRepository.save(transaction);

        return "Money Deposited successfully";
     }

     public String withdrwaMoney(Long accountId, Double amount){
        SavingAccount account=repository.findById(accountId)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Account not found with id : "+ accountId
                )
                );

        if(account.getBalance()<amount){
            throw new RuntimeException(
                    "Insufficient Fund!"
            );

        }
         account.setBalance(
                 account.getBalance()-amount
         );

        Transaction transaction=new Transaction();

        transaction.setAccountId(accountId);
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setBalance(account.getBalance());

        transactionRepository.save(transaction);

         return "Money Withdwar Successfully";
     }

     public List<Transaction> getTransactionHistory(Long accounId){
        return transactionRepository.findByAccountId(accounId);
     }

     public void sendAccountCreatedEvent(SavingAccount account){
        String message="Account Created : "
                + account.getId()
                +", customer : "
                + account.getCustomerName()
                + ", mobile : "
                +account.getMobile();

        kafkaTemplate.send("account-created",message);
     }

}
