package com.example.controller;
import com.example.demo.DateClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

// Import the above-defined classes
// to use the properties of those
// classes

// Creating the REST controller
@RestController
@RequestMapping(path = "/date")
public class DateController {

    @Value("${url}")
    String url;
    @Value("${user}")
    String username;
    @Value("${password}")
    String password;

    @Value("${firstTableName}")
    String firstTableName;

    // Implementing a GET method
    // to get the current date at ISO 8601 format at Zulu timezone
    @GetMapping(path = "/", produces = "application/json")
    public DateClass getFormattedDate()  {

        /*MySQLConnector mySQLConnector = new MySQLConnector();
        mySQLConnector.connect(this.url, this.username, this.password);

        Map<String, String> databaseRecordData = new HashMap<>();
        databaseRecordData.put("firstname", "Ayeah");
        databaseRecordData.put("lastname", "Meyah");

        mySQLConnector.insertDatabaseRecord(firstTableName, databaseRecordData);

        mySQLConnector.disconnect();*/

        return new DateClass();
    }
}
