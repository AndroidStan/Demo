package com.example.controller;

import com.example.DemoApplication;
import com.example.demo.DateClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DemoApplication.class)
@ActiveProfiles("test")
public class DateControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testGetISODate() {
        String url = "http://localhost:" + port + "/date/";
        System.out.println("GET: url = " + url);

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("response.getStatusCode() = " + response.getStatusCode());
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        System.out.println("response.getBody() = " + response.getBody());

        DateClass dateClass = new DateClass();
        String formattedDate = dateClass.getFormattedDate().split("\\.",2)[0];
        System.out.println("generatedFormattedDate = " + formattedDate);

        Boolean dateContained = response.getBody().contains(formattedDate);

        assertThat(dateContained).isTrue();
    }

}
