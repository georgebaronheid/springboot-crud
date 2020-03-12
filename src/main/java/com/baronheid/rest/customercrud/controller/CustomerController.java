package com.baronheid.rest.customercrud.controller;

import com.baronheid.rest.customercrud.domain.Customer;
import com.baronheid.rest.customercrud.exception.ResourceNotFoundException;
import com.baronheid.rest.customercrud.repository.CustomerRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @NonNull
    private final CustomerRepository customerRepository;

    @Autowired
    // Constructor injection
    public CustomerController(@NonNull final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(value = "/customers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer registerUser (@RequestBody final Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping(value = "/customers")
    public Page<Customer> all (final Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @GetMapping(value = "/customers/{customerId}")
    public Customer findByCustomerId(@PathVariable final Integer customerId) throws ResourceNotFoundException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Custom [customerId="+customerId+"] can't be found"));
    }

    @DeleteMapping(value = "/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable final Integer customerId) throws ResourceNotFoundException {
        return customerRepository.findById(customerId)
                .map(
                customer -> {
                    customerRepository.delete(customer);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));
    };


    @PatchMapping(value = "/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable final Integer customerId,
                                                   @RequestBody final Customer newCustomer) throws ResourceNotFoundException {

        return customerRepository.findById(customerId).map(
                customer -> {
                    customer.setCustomerName(newCustomer.getCustomerName());
                    customer.setDateofBirth(newCustomer.getDateofBirth());
                    customer.setPhoneNumber(newCustomer.getPhoneNumber());
                    customerRepository.save(customer);
                    return ResponseEntity.ok(customer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be saved"));
    }

}
