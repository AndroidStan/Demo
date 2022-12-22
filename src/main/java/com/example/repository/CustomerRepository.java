package com.example.repository;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import com.example.model.Customer;
import com.example.connectivity.DBConnector;
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
                              @Value("${customerTableName}") String customerTableName) {
        this.url = dbUrl;
        this.username = username;
        this.password = password;
        this.customerTableName = customerTableName;
    }

    public Customer insertCustomer(Customer customer){
        Boolean recordSuccessfullyInserted;

        DBConnector dbConnector = new DBConnector();
        dbConnector.connect(this.url, this.username, this.password);

        Map<String, String> databaseRecordData = new HashMap<>();
        databaseRecordData.put("firstname", customer.getFirstName());
        databaseRecordData.put("lastname", customer.getLastName());

        recordSuccessfullyInserted = dbConnector.insertDatabaseRecord(this.customerTableName, databaseRecordData);

        dbConnector.disconnect();

        return recordSuccessfullyInserted ? customer : null;
    }

    public Customer readCustomerByFirstName(String firstName) {
        DBConnector dbConnector = new DBConnector();
        dbConnector.connect(this.url, this.username, this.password);

        Customer customer = dbConnector.readRecordBySearchString(this.customerTableName, "firstname", firstName);

        dbConnector.disconnect();
        return customer;
    }

    public Customer readCustomerById(String customerId) {
        DBConnector dbConnector = new DBConnector();
        dbConnector.connect(this.url, this.username, this.password);

        Customer customer = dbConnector.readRecordBySearchString(this.customerTableName, "id", customerId);

        dbConnector.disconnect();
        return customer;
    }
    public List<Customer> readAllCustomers(){
        DBConnector dbConnector = new DBConnector();
        dbConnector.connect(this.url, this.username, this.password);

        List<Customer> customers = dbConnector.readAllRecords(this.customerTableName);

        dbConnector.disconnect();
        return customers;
    }
}
