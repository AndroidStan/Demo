package com.example.controller;

import com.example.DemoApplication;
import com.example.connectivity.MySQLConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = DemoApplication.class)
@ActiveProfiles("test")
class CustomerControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate = new TestRestTemplate();
	@Value("${url}")
	private String url;
	@Value("${user}")
	private String username;
	@Value("${password}")
	private String password;

	@BeforeEach
	public void init(){
		MySQLConnector mySQLConnector = new MySQLConnector();
		mySQLConnector.connect(this.url, this.username, this.password);
		mySQLConnector.createCustomerTable();
		mySQLConnector.disconnect();
	}

	@Test
	void testLoadAll() {
		String url = "http://localhost:" + port + "/customers";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
		System.out.println("port = " + port);
	}

}
