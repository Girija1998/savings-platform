package com.saving.service.controller;

import com.saving.service.dto.DepositRequest;
import com.saving.service.dto.SavingRequest;
import com.saving.service.dto.WithdrawRequest;
import com.saving.service.entity.SavingAccount;
import com.saving.service.entity.Transaction;
import com.saving.service.service.SavingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    private SavingService savingService;

    @PostMapping("/account/create")
    public ResponseEntity<String> createAccount(
            @Valid @RequestBody SavingRequest request
    ) {
        String response =
                savingService.createAccount(request);
        return ResponseEntity.ok(response);
    }

     @GetMapping("/all")
     public ResponseEntity<List<SavingAccount>> gelAllAccounts(){
        List<SavingAccount> accounts= savingService.getAllAccounts();

        return ResponseEntity.ok(accounts);
     }

     @GetMapping("/account/{id}")
     public ResponseEntity<SavingAccount> getAccountById(
             @PathVariable Long id
     ){
        return ResponseEntity.ok(
                savingService.getAccountById(id)
        );
     }

     @PutMapping("/update/{id}")
     public ResponseEntity<String> updateAccount(
             @PathVariable Long id,
             @Valid
             @RequestBody SavingRequest request
     ){
        String response=savingService.updateAccount(id,request);
        return ResponseEntity.ok(response);
     }

     @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteAccount(
             @PathVariable Long id
     )
     {
         String response = savingService.deleteAccount(id);

         return ResponseEntity.ok(response);
     }

     @GetMapping("/mobile/{mobile}")
     public ResponseEntity<SavingAccount> getAccountByMobile(
             @PathVariable String mobile
     ){
        return ResponseEntity.ok(
                savingService.getAccountByMobile(mobile)
        );
     }

     @GetMapping("/customer/{customerName}")
     public ResponseEntity<List<SavingAccount>> getAccountByCustomerName(
             @PathVariable String customerName
     )
     {

         return ResponseEntity.ok(

                 savingService
                         .getAccountByCustomerName(
                                 customerName
                         )
         );
     }

     @GetMapping("/page")
      public ResponseEntity<Page<SavingAccount>> getAccountWithPagination(
              @RequestParam int page,
              @RequestParam int size
      )
      {

          return ResponseEntity.ok(savingService.getAccountWithPagination(
                  page, size));
      }

      @GetMapping("/sort")
      public ResponseEntity<List<SavingAccount>>
      getSortedAccounts(){
          return ResponseEntity.ok(

                  savingService
                          .getAccountsSortedByName()
          );
      }

      @PostMapping("/deposit")
      public ResponseEntity<String> depositMoney(
              @Valid
              @RequestBody DepositRequest request
              )
      {
          String response=savingService.depositMoney(
                  request.getAccountId(),
                  request.getAmount()
          );

          return ResponseEntity.ok(response);
      }

      @PostMapping("/withdraw")
      public ResponseEntity<String> withdrawMoney(
              @Valid
              @RequestBody WithdrawRequest request
              ){
        String response=savingService.withdrwaMoney(
                request.getAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok(response);
      }

      @GetMapping("transactions/{accountId}")
      public ResponseEntity<List<Transaction>> getTransactionHistory(
              @PathVariable Long accountId
      ){
         return ResponseEntity.ok(
                 savingService.getTransactionHistory(accountId)
         );
      }

   }

















