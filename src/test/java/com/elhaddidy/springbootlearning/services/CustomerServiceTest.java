package com.elhaddidy.springbootlearning.services;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import com.elhaddidy.springbootlearning.exception.CustomerIsUnderAgeException;
import com.elhaddidy.springbootlearning.exception.CustomerNotFoundException;
import com.elhaddidy.springbootlearning.exception.DuplicateResourceException;
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
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getCustomers_test() {
        when(customerRepository.findAll()).thenReturn(List.of(customerDAO));

        assertThat(customerService.getCustomers().size()).isEqualTo(1);
        assertThat(customerService.getCustomers().get(0).getName()).isEqualTo("Karel");
    }

    @Test
    void getCustomer_test() {
        when(customerRepository.getReferenceById(1)).thenReturn(customerDAO);
        assertThat(customerService.getCustomer(1)).isEqualTo(customer);
    }

    @Test
    void getCustomer_test_CustomerNotFoundException() {
        when(customerRepository.getReferenceById(-1)).thenThrow(new EntityNotFoundException("E"));
        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomer(-1);
        });
        assertThat(e).hasMessage("E");
    }

    @Test
    void addCustomer_test_DuplicateResourceException1() {
        when(customerRepository.existsCustomerDAOByName("Karel")).thenReturn(true);

        DuplicateResourceException e1 = assertThrows(DuplicateResourceException.class, () -> {
            customerService.addCustomer(customer);
        });
        assertThat(e1).hasMessage("Name Karel is already taken");
    }

    @Test
    void addCustomer_test_DuplicateResourceException2() {
        when(customerRepository.existsCustomerDAOByEmail("karel@gmail.com")).thenReturn(true);

        DuplicateResourceException e2 = assertThrows(DuplicateResourceException.class, () -> {
            customerService.addCustomer(customer);
        });
        assertThat(e2).hasMessage("Email karel@gmail.com is already taken");
    }

    @Test
    void addCustomer_test_CustomerIsUnderAgeException() {
        customer.setAge(15);

        CustomerIsUnderAgeException e3 = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.addCustomer(customer);
        });
        assertThat(e3).hasMessage("Karel is underage");
    }


    @Test
    void addCustomers_test_CustomerIsUnderAgeException1() {
        //one customer is underage
        customer.setAge(15);

        CustomerIsUnderAgeException e = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.addCustomers(List.of(customer));
        });

        assertThat(e).hasMessage("Customer [Karel] is underage");
    }

    @Test
    void addCustomers_test_CustomerIsUnderAgeException2() {
        // more than one customer is underage
        customer.setAge(15);
        Customer customer2 = new Customer(2, "David", "david@gmail.com", 14);

        CustomerIsUnderAgeException e = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.addCustomers(List.of(customer, customer2));
        });
        assertThat(e).hasMessage("Customers [Karel, David] are underage");
    }

    @Test
    void deleteCustomer_test_CustomerNotFoundException() {
        when(customerRepository.getReferenceById(-1)).thenThrow(new EntityNotFoundException());

        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.deleteCustomer(-1);
        });
        assertThat(e).hasMessage("There is not customer with -1 id to be deleted");
    }

    @Test
    void updateCustomerEmail_test_CustomerNotFoundException() {

        when(customerRepository.existsCustomerDAOById(-1)).thenReturn(true);

        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomerEmail(-1, "pavel@gmail.com");
        });
        assertThat(e).hasMessage("There is not customer with -1 id to be updated");
    }

    @Test
    void updateCustomerEmail_test_DuplicateResourceException() {

        when(customerRepository.existsCustomerDAOByEmail("pavel@gmail.com")).thenReturn(true);

        DuplicateResourceException e = assertThrows(DuplicateResourceException.class, () -> {
            customerService.updateCustomerEmail(-1, "pavel@gmail.com");
        });
        assertThat(e).hasMessage("Email pavel@gmail.com is already taken");
    }

    @Test
    void updateCustomer_test_CustomerNotFoundException() {

        when(customerRepository.existsCustomerDAOById(1)).thenReturn(true);

        CustomerNotFoundException e = assertThrows(CustomerNotFoundException.class, () -> {
            customerService.updateCustomer(customer);
        });
        assertThat(e).hasMessage("There is not customer with 1 id to be updated");
    }

    @Test
    void updateCustomer_test_DuplicateResourceException1() {

        when(customerRepository.existsCustomerDAOByName("Karel")).thenReturn(true);

        DuplicateResourceException e = assertThrows(DuplicateResourceException.class, () -> {
            customerService.updateCustomer(customer);
        });
        assertThat(e).hasMessage("Name Karel is already taken");
    }

    @Test
    void updateCustomer_test_DuplicateResourceException2() {

        when(customerRepository.existsCustomerDAOByEmail("karel@gmail.com")).thenReturn(true);

        DuplicateResourceException e = assertThrows(DuplicateResourceException.class, () -> {
            customerService.updateCustomer(customer);
        });
        assertThat(e).hasMessage("Email karel@gmail.com is already taken");
    }

    @Test
    void updateCustomer_test_CustomerIsUnderAgeException() {
        customer.setAge(5);
        CustomerIsUnderAgeException e = assertThrows(CustomerIsUnderAgeException.class, () -> {
            customerService.updateCustomer(customer);
        });
        assertThat(e).hasMessage("Customer is underage");
    }



}