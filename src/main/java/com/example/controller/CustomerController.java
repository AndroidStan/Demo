package com.example.controller;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    @Autowired
    private CustomerService service;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Customer insertCustomer(@RequestBody Customer customer)
    {
        return service.insertCustomer(customer);
    }

    @GetMapping(path = "/search", produces = "application/json")
    public Customer readCustomerByFirstName(@RequestParam("firstName") String firstName){
        return service.readCustomerByFirstName(firstName);
    }

    @GetMapping(path = "/{customerId}", produces = "application/json")
    public Customer readCustomerByID(@PathVariable String customerId){
        return service.readCustomerById(customerId);
    }

    @GetMapping(produces = "application/json")
    public List<Customer> readAllCustomers(){
        return service.readAllCustomers();
    }
}
