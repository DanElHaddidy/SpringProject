package com.elhaddidy.springbootlearning.data.mappers;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerDAOtoCustomer implements Function<CustomerDAO, Customer> {

    @Override
    public Customer apply(CustomerDAO customerDAO) {
        return new Customer(
                customerDAO.getId(),
                customerDAO.getName(),
                customerDAO.getEmail(),
                customerDAO.getAge()
        );
    }
}
