package com.example.demo;

import lombok.Getter;
import lombok.Setter;

// Creating an entity Employee
public class Employee {
    public Employee() {}

    // Parameterized Constructor
    // to assign the values
    // to the properties of
    // the entity
    public Employee(
            Integer id, String firstName,
            String lastName, String email)
    {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String firstName;

    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String email;

    // Overriding the toString method
    // to find all the values
    @Override
    public String toString()
    {
        return "Employee [id="
                + id + ", firstName="
                + firstName + ", lastName="
                + lastName + ", email="
                + email + "]";
    }

}
