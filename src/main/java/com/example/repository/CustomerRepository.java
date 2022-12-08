package com.example.repository;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.example.model.Customer;
import com.example.connectivity.MySQLConnector;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Value;

@Repository
public class CustomerRepository {

    private final String url;
    private final String username;
    private final String password;
    private final String customerTableName;

    public CustomerRepository(@Value("${url}") String dbUrl,
                              @Value("${user}") String username,
                              @Value("${password}") String password,
                              @Value("$(customerTableName)") String customerTableName) {
        this.url = dbUrl;
        this.username = username;
        this.password = password;
        this.customerTableName = customerTableName;
    }

    public Customer insertCustomer(Customer customer){
        Boolean recordSuccessfullyInserted;

        MySQLConnector mySQLConnector = new MySQLConnector();
        mySQLConnector.connect(this.url, this.username, this.password);

        Map<String, String> databaseRecordData = new HashMap<>();
        databaseRecordData.put("firstname", customer.getFirstName());
        databaseRecordData.put("lastname", customer.getLastName());

        recordSuccessfullyInserted = mySQLConnector.insertDatabaseRecord(this.customerTableName, databaseRecordData);

        mySQLConnector.disconnect();

        return recordSuccessfullyInserted ? customer : null;
    }

    public Customer readCustomerByFirstName(String firstName) {
        MySQLConnector mySQLConnector = new MySQLConnector();
        mySQLConnector.connect(this.url, this.username, this.password);

        Customer customer = mySQLConnector.readRecordBySearchString(this.customerTableName, "firstname", "Ayeah");

        mySQLConnector.disconnect();
        return customer;
    }
    public List<Customer> readAllCustomers(){
        MySQLConnector mySQLConnector = new MySQLConnector();
        mySQLConnector.connect(this.url, this.username, this.password);

        List<Customer> customers = mySQLConnector.readAllRecords(this.customerTableName);

        mySQLConnector.disconnect();
        return customers;
    }
}
