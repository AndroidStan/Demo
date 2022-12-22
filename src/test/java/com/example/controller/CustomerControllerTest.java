package com.example.controller;

import com.example.DemoApplication;
import com.example.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = DemoApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate = new TestRestTemplate();
	@Value("${url}")
/*	private String url;
	@Value("${user}")
	private String username;
	@Value("${password}")
	private String password;*/

	@BeforeEach
	public void init(){
		/*MySQLConnector mySQLConnector = new MySQLConnector();
		mySQLConnector.connect(this.url, this.username, this.password);
		mySQLConnector.createCustomerTable();
		mySQLConnector.disconnect();*/
	}

	@ParameterizedTest(name="Adding customer {index} => firstname={0}, lastName={1}")
	@CsvSource({ "Pencho, Baichev", "Stako, Mishev"})
	@Order(1)
	void testInsertCustomerAndValidateResponse(String firstName, String lastName) {
		String url = "http://localhost:" + port + "/customers/";
		System.out.println("POST: url = " + url);

		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);

		ResponseEntity<String> response = restTemplate.postForEntity(url, customer, String.class);

		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		System.out.println("response.getBody() = " + response.getBody());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();


		String customerJson;
		try {
			customerJson = new ObjectMapper().writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		try {
			JSONAssert.assertEquals(customerJson, response.getBody(), JSONCompareMode.STRICT);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	@Order(2)
	void testGetCustomerById(){
		String url = "http://localhost:" + port + "/customers/1";
		System.out.println("GET: url = " + url);

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		System.out.println("response.getBody() = " + response.getBody());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		Customer customer = new Customer();
		customer.setFirstName("Pencho");
		customer.setLastName("Baichev");

		Customer responseCustomer;

		try {
			responseCustomer = new ObjectMapper().readValue(response.getBody(), Customer.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		assertThat(responseCustomer).usingRecursiveComparison().isEqualTo(customer);
	}

	@ParameterizedTest(name="Verifying customer with firstname {0} exists!")
	@CsvSource({ "Pencho, Baichev"})
	@Order(3)
	void testGetSingleCustomer(String firstName, String lastName) {
		String url = "http://localhost:" + port + "/customers/search?firstName=" + firstName;
		System.out.println("GET: url = " + url);

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		System.out.println("response.getBody() = " + response.getBody());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);

		Customer responseCustomer;

		try {
			responseCustomer = new ObjectMapper().readValue(response.getBody(), Customer.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		assertThat(responseCustomer).usingRecursiveComparison().isEqualTo(customer);
	}

	@ParameterizedTest(name="Verifying {0},{1} and {2},{3} exist!")
	@CsvSource({ "Pencho, Baichev, Stako, Mishev"})
	@Order(4)
	void testGetCustomersCollection(String customer1FirstName, String customer1LastName, String customer2FirstName, String customer2LastName ) {
		String url = "http://localhost:" + port + "/customers";
		System.out.println("GET: url = " + url);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		System.out.println("response.getBody() = " + response.getBody());

		List<Customer> customers = new ArrayList<>();

		Customer firstCustomer = new Customer();
		firstCustomer.setFirstName(customer1FirstName);
		firstCustomer.setLastName(customer1LastName);
		customers.add(firstCustomer);

		Customer secondCustomer = new Customer();
		secondCustomer.setFirstName(customer2FirstName);
		secondCustomer.setLastName(customer2LastName);
		customers.add(secondCustomer);

		String customersJson;
		try {
			customersJson = new ObjectMapper().writeValueAsString(customers);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		try {
			JSONAssert.assertEquals(customersJson, response.getBody(), JSONCompareMode.STRICT);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
