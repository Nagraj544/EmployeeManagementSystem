package com.springboot.backend;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import com.springboot.backend.model.Employee;
import com.springboot.backend.repository.EmployeeRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	//JUnit test for saveEmployee
	@Test
	@Order(1)
	@Rollback(value = true)
	public void saveEmployee() {
		
		Employee employee = new Employee(1, "nagraj", "Keshwar", "nagraj@gmail.com");
		
		employeeRepository.save(employee);
		
		Assertions.assertThat(employee.getId()).isGreaterThan(0);
	}
	
	
	//JUnit test for getEmployees
	@Test
	@Order(2)	
	public void getEmployeeTest() {
		
		Employee employee = employeeRepository.findById(2L).get();
		
		Assertions.assertThat(employee.getId()).isEqualTo(2L);
	}
	
	
	//JUnit test for to get all Employees
	@Test
	@Order(3)
	public void getListOfEmployeeTest() {
		
		List<Employee> employees = employeeRepository.findAll();
		
		Assertions.assertThat(employees.size()).isGreaterThan(0);
	}
	
	
	//JUnit test for to update Employees
	@Test
	@Order(4)
	@Rollback(value = true)
	public void updateEmployeeTest() {
		
		Employee employee = employeeRepository.findById(2L).get();
		employee.setEmail("tony@gmail.com");
		
		Employee employeeUpdated = employeeRepository.save(employee);
		Assertions.assertThat(employeeUpdated.getEmail()).isEqualTo("tony@gmail.com");
	}
	
	
	//JUnit test for to delete Employees
	@Test
	@Order(5)
	@Rollback(value = true)
	public void deleteEmployeeTest() {
		
		Employee employee = employeeRepository.findById(2L).get();
		employeeRepository.delete(employee);
		
//		employeeRepository.deleteById(17L);
		
		Employee employee1 = null;
		
		Optional<Employee> optionalEmployee = employeeRepository.findByEmail("tony@gmail.com");
		
		if(optionalEmployee.isPresent()) {
			employee1 = optionalEmployee.get();
		}
		
		Assertions.assertThat(employee1).isNull();
		
		
	}
}
