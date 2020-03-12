package com.baronheid.rest.customercrud.controller;

import com.baronheid.rest.customercrud.domain.Account;
import com.baronheid.rest.customercrud.domain.Customer;
import com.baronheid.rest.customercrud.exception.ResourceNotFoundException;
import com.baronheid.rest.customercrud.repository.AccountRepository;
import com.baronheid.rest.customercrud.repository.CustomerRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    @NonNull
    private final CustomerRepository customerRepository;

    @NonNull
    private final AccountRepository accountRepository;

    @Autowired
    private AccountController(@NonNull final CustomerRepository customerRepository,
                              @NonNull final AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = "/customer/{customerId}/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account save(@PathVariable final Integer customerId,
                        @RequestBody final Account account) throws ResourceNotFoundException {
        return customerRepository.findById(customerId).map(
                customer -> {
                    account.setCustomer(customer);
                    return accountRepository.save(account);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));
    }

    @GetMapping(value = "/customer/{customerId}/accounts")
    public Page<Account> all (@PathVariable final Integer customerId, final Pageable pageable) {
        return accountRepository.findByCustomerCustomerId(customerId, pageable);
    }

    @DeleteMapping(value = "/customer/{customerId}/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable final Integer customerId,
                                           @PathVariable final Integer accountId) throws ResourceNotFoundException {

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found");
        }

        return accountRepository.findById(accountId).map(account ->{
            accountRepository.delete(account);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Account [accountId="+accountId+"] can't be deleted"));
    }

    @PutMapping(value = "/customer/{customerId}/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable final Integer customerId,
                                                 @PathVariable final Integer accountId,
                                                 @RequestBody Account newAccount) throws ResourceNotFoundException {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));

        return accountRepository.findById(accountId).map(account -> {
            newAccount.setCustomer(customer);
            accountRepository.save(newAccount);
            return ResponseEntity.ok(newAccount);
        }).orElseThrow(()-> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be updated"));
    }

}
