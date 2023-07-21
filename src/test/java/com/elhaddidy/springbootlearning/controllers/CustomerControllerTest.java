package com.elhaddidy.springbootlearning.controllers;

import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.services.CustomerService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerService customerService;

    Customer customer1;
    Customer customer2;

    List<Customer> customerList;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(1, "Karel", "karel@gmail.com", 22);
        customer2 = new Customer(2,"Honza","honza@gmail.com",30);
        customerList = new ArrayList<>();
        customerList.add(customer1);
        customerList.add(customer2);
    }


    @Test
    void getCustomersTest() throws Exception {
        when(customerService.getCustomers()).thenReturn(customerList);

        this.mockMvc.perform(get("http://localhost:8080/api/v1/customers"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getCustomerTest() throws Exception {
        when(customerService.getCustomer(1)).thenReturn(customer1);

        this.mockMvc.perform(get("http://localhost:8080/api/v1/customers/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void addCustomerTest() throws Exception {
        String requestJSON = new Gson().toJson(customer1);

        this.mockMvc.perform(
                post("http://localhost:8080/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJSON))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    void addCustomersTest() throws Exception {
        String requestJSON = new Gson().toJson(customerList);

        this.mockMvc.perform(
                        post("http://localhost:8080/api/v1/customers/list")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJSON))
                .andDo(print()).andExpect(status().isOk());
    }



    @Test
    void deleteCustomerTest() throws Exception {
        this.mockMvc.perform(delete("http://localhost:8080/api/v1/customers/1"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateCustomerEmailTest() throws Exception {
        String requestJSON = new Gson().toJson(customer1);

        this.mockMvc.perform(
                        patch("http://localhost:8080/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJSON))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateCustomerTest() throws Exception {
        String requestJSON = new Gson().toJson(customer1);

        this.mockMvc.perform(
                        put("http://localhost:8080/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJSON))
                .andDo(print()).andExpect(status().isOk());
    }
}