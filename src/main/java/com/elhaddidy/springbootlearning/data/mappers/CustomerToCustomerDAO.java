package com.elhaddidy.springbootlearning.data.mappers;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
public class CustomerToCustomerDAO implements Function<Customer, CustomerDAO> {


    @Override
    public CustomerDAO apply(Customer customer) {
        return new CustomerDAO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
    }
}
