package com.example.controller;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Customer> insertCustomer(@RequestBody Customer customer)
    {
        service.insertCustomer(customer);
        URI location= ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand().toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/", produces = "application/json")
    public Customer readCustomerByFirstName(@RequestParam("firstName") String firstName){
        return service.readCustomerByFirstName(firstName);
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<Customer> readAllCustomers(){
        return service.readAllCustomers();
    }
}
