package com.example.service;

import com.example.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.repository.CustomerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Customer insertCustomer(Customer customer) {
        return repository.insertCustomer(customer);
    }

    public Customer readCustomerByFirstName(String firstName) {
        return repository.readCustomerByFirstName(firstName);
    }

    public List<Customer> readAllCustomers() {
        return repository.readAllCustomers();
    }
}
