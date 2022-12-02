package com.example.demo;
import org.springframework.web.bind.annotation.*;
import com.example.demo.DateClass;

// Import the above-defined classes
// to use the properties of those
// classes

// Creating the REST controller
@RestController
@RequestMapping(path = "/date")
public class DateController {

    // Implementing a GET method
    // to get the current date at ISO 8601 format at Zulu timezone
    @GetMapping(path = "/", produces = "application/json")
    public DateClass getFormattedDate()
    {
        return new DateClass();
    }
}
