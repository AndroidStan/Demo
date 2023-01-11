package com.example.controller;

import com.example.DemoApplication;
import com.example.model.Employee;
import com.example.model.Employees;
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
class EmployeeControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate = new TestRestTemplate();


	@ParameterizedTest(name="Adding employee {index} => firstname={0}, lastName={1}")
	@CsvSource({ "2, Pencho, Baichev, pencho@baichev.mail"})
	@Order(1)
	void testInsertEmployeeAndValidateResponse(Integer id, String firstName, String lastName, String email) {
		String url = "http://localhost:" + port + "/employees/";
		System.out.println("POST: url = " + url);

		Employee employee = new Employee(id, firstName, lastName, email);

		ResponseEntity<String> response = restTemplate.postForEntity(url, employee, String.class);

		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		System.out.println("response.getBody() = " + response.getBody());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		String employeeJson;
		try {
			employeeJson = new ObjectMapper().writeValueAsString(employee);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		try {
			JSONAssert.assertEquals(employeeJson, response.getBody(), JSONCompareMode.STRICT);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@ParameterizedTest(name="Verifying {0},{1}, {2}, {3} and {4},{5}, {6}, {7} exist!")
	@CsvSource({ "1, Prem, Tiwari, chapradreams@gmail.com, 2, Pencho, Baichev, pencho@baichev.mail"})
	@Order(2)
	void testGetEmployeesCollection(Integer employee1ID, String employee1FirstName, String employee1LastName, String employee1Email,
									Integer employee2ID, String employee2FirstName, String employee2LastName, String employee2Email ) {
		String url = "http://localhost:" + port + "/employees/";
		System.out.println("GET: url = " + url);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("response.getStatusCode() = " + response.getStatusCode());
		assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

		System.out.println("response.getBody() = " + response.getBody());

		List<Employee> employeesList = new ArrayList<>();

		Employee firstEmployee = new Employee(employee1ID, employee1FirstName, employee1LastName, employee1Email);
		employeesList.add(firstEmployee);

		Employee secondEmployee = new Employee(employee2ID, employee2FirstName, employee2LastName, employee2Email);
		employeesList.add(secondEmployee);

		Employees employees = new Employees();
		employees.setEmployeeList(employeesList);

		String employeesJson;
		try {
			employeesJson = new ObjectMapper().writeValueAsString(employees);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		try {
			JSONAssert.assertEquals(employeesJson, response.getBody(), JSONCompareMode.STRICT);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
