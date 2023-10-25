package com.customeer.customeer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.customeer.customeer.models.Customer;
//import com.customeer.customeer.ui.components.CustomerForm;
import com.customeer.customeer.repositories.CustomerRepository;
import com.customeer.customeer.services.CustomerService;
//import com.customeer.customeer.ui.CustomerView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CustomeerApplicationTests {

	@Autowired
	private CustomerService customerService;

	@MockBean
	private CustomerRepository customerRepository;

	private Customer customer1;
	private Customer customer2;

	@BeforeEach
	public void setUp() {
		customer1 = new Customer("John Doe", "john@example.com", "Notes for John");
		customer2 = new Customer("Jane Doe", "jane@example.com", "Notes for Jane");
		List<Customer> customers = Arrays.asList(customer1, customer2);

		when(customerRepository.findAll()).thenReturn(customers);
	}

	// Context Loading
	@Test
	void contextLoads() {
	}

	// Customer Entity Tests
	// -------------------------------------------------
	@Test
	public void testCustomerAttributes() {
		assertEquals("John Doe", customer1.getName());
		assertEquals("john@example.com", customer1.getEmail());
		assertEquals("Notes for John", customer1.getNotes());
	}

	// Customer Repository Tests
	// -------------------------------------------------
	@Test
	public void testSaveCustomer() {
		when(customerRepository.save(customer1)).thenReturn(customer1);
		Customer savedCustomer = customerService.save(customer1);
		assertNotNull(savedCustomer);
		assertEquals(customer1.getName(), savedCustomer.getName());
	}

	@Test
	public void testDeleteCustomer() {
		doNothing().when(customerRepository).delete(customer1);
		customerService.deleteById(customer1.getId());
		verify(customerRepository, times(1)).deleteById(customer1.getId());
	}

	// Customer Service Tests
	// -------------------------------------------------
	@Test
	public void testCustomerServiceFindAll() {
		List<Customer> result = customerService.findAll();
		assertEquals(2, result.size());
		assertEquals(customer1.getName(), result.get(0).getName());
	}

	@Test
	public void testCustomerServiceSave() {
		when(customerRepository.save(customer1)).thenReturn(customer1);
		Customer result = customerService.save(customer1);
		assertEquals(customer1.getName(), result.getName());
	}

	@Test
	public void testCustomerServiceDelete() {
		doNothing().when(customerRepository).delete(customer1);
		customerService.deleteById(customer1.getId());
		verify(customerRepository, times(1)).deleteById(customer1.getId());
	}

	/*
	 TODO: Customer View Tests
	 View and Form Tests
	 -------------------------------------------------
		@Test
		public void testCustomerFormBinding() {
			CustomerForm customerForm = new CustomerForm(customerService);
			customerForm.setCustomer(customer1);
			assertEquals(customer1.getName(), customerForm.getName().getValue());
			assertEquals(customer1.getEmail(), customerForm.getEmail().getValue());
		}

		@Test
		public void testCustomerViewFilter() {
			CustomerView customerView = new CustomerView(customerService);
			customerView.getFilterText().setValue(customer1.getEmail());
			customerView.updateList();
			assertEquals(1, customerView.getGrid().getItems().size());
		}
	*/
}
