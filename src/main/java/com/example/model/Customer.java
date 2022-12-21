package com.example.model;

import lombok.Getter;
import lombok.Setter;

public class Customer {
    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    public Customer() {}
    public Customer(String firstName, String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString()
    {
        return "Customer [firstName=" + firstName +
                ", lastName=" + lastName + "]";
    }

}
