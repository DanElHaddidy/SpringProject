package com.elhaddidy.springbootlearning.domain.repository;


import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
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

    CustomerToCustomerDAO customerToCustomerDAO;
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


    @Test
    void existsCustomerDAOById_est(){
        List<CustomerDAO> list = customerRepository.findAll();

        assertThat(customerRepository.existsCustomerDAOById(list.get(0).getId())).isTrue();
        assertThat(customerRepository.existsCustomerDAOById(-5)).isFalse();
    }


    @Test
    void existsCustomerDAOByName_test(){
        assertThat(customerRepository.existsCustomerDAOByName("Karel")).isTrue();
        assertThat(customerRepository.existsCustomerDAOByName("Peter")).isFalse();
    }


    @Test
    void existsCustomerDAOByEmail_test(){
       assertThat(customerRepository.existsCustomerDAOByEmail("karel@gmail.com")).isTrue();
       assertThat(customerRepository.existsCustomerDAOByEmail("petr@gmail.com")).isFalse();
    }



}
