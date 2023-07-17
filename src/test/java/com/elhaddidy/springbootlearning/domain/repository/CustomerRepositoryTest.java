package com.elhaddidy.springbootlearning.domain.repository;


import com.elhaddidy.springbootlearning.data.mappers.CustomerToCustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private CustomerToCustomerDAO customerToCustomerDAO;
    Customer customer;

    @BeforeEach
    void setUp() {
        customerToCustomerDAO = new CustomerToCustomerDAO();
        customer = new Customer(1, "Karel", "karel@gmail.com", 22);
        customerRepository.save(
                customerToCustomerDAO.apply(customer)
        );
    }

    @AfterEach
    void tearDown() {
        customer = null;
        customerRepository.deleteById(1);
    }

    //Test case success
    @Test
    void testFindByName_Found() {
        List<Customer> customerList = customerRepository.findByName("Karel");


        assertThat(customerList.get(0).getId()).isEqualTo(1);
        assertThat(customerList.get(0).getName()).isEqualTo("Karel");

    }

    //Test case failure

    @Test
    void testFindByName_NotFound() {
        List<Customer> customerList = customerRepository.findByName("Honza");
        assertThat(customerList.isEmpty()).isTrue();

    }

}
