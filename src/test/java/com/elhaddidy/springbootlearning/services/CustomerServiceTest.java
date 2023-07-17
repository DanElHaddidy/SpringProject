package com.elhaddidy.springbootlearning.services;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import com.elhaddidy.springbootlearning.exception.CustomerIsUnderAgeException;
import com.elhaddidy.springbootlearning.exception.CustomerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    AutoCloseable autoCloseable;
    CustomerDAO customerDAO;
    Customer customer;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
        customerDAO = new CustomerDAO(1, "Karel", "karel@gmail.com", 22);
        customer = new Customer(1, "Karel", "karel@gmail.com", 22);


    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();

    }

    @Test
    void getCustomersTest() {


        when(customerRepository.findAll()).thenReturn(List.of(customerDAO));
        assertThat(customerService.getCustomers().size()).isEqualTo(1);
        assertThat(customerService.getCustomers().get(0).getName()).isEqualTo("Karel");
    }

    @Test
    void getCustomerTest() {


        when(customerRepository.getReferenceById(1)).thenReturn(customerDAO);
        assertThat(customerService.getCustomer(1)).isEqualTo(customer);

        // customer not found exception test (no customer with given id)
        when(customerRepository.getReferenceById(-1)).thenThrow(new EntityNotFoundException("E"));
        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomer(-1);
        });
        assertThat(e).hasMessage("E");
    }

    @Test
    void addCustomerTest() {



        assertThat(customerService.addCustomer(customer)).isEqualTo("Success");

        // customer is underage exception test
        customer.setAge(15);
        CustomerIsUnderAgeException e = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.addCustomer(customer);
        });
        assertThat(e).hasMessage("Customer is underage");
    }

    @Test
    void addCustomersTest() {

        assertThat(customerService.addCustomers(List.of(customer))).isEqualTo("Success");
        customer.setAge(15);

        // customer us underage exception test
        CustomerIsUnderAgeException e = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.addCustomers(List.of(customer));
        });

        assertThat(e).hasMessage("One of the customers is underage");

    }

    @Test
    void deleteCustomerTest() {
        assertThat(customerService.deleteCustomer(1)).isEqualTo("Success");

        // customer not found exception test (no customer with given id)
        when(customerRepository.getReferenceById(-1)).thenThrow(new EntityNotFoundException());
        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(-1);
        });
        assertThat(e).hasMessage("There is not customer with this id to be deleted");

    }

    @Test
    void updateCustomerEmailTest() {

        when(customerRepository.getReferenceById(1)).thenReturn(customerDAO);
        assertThat(customerService.updateCustomerEmail(1,"honza@gmail.com")).isEqualTo("Success");

    }

    @Test
    void updateCustomerTest() {

        when(customerRepository.getReferenceById(1)).thenReturn(customerDAO);
        assertThat(customerService.updateCustomer(customer)).isEqualTo("Success");

    }
}