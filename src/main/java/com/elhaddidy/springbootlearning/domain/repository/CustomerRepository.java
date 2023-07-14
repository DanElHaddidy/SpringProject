package com.elhaddidy.springbootlearning.domain.repository;

import com.elhaddidy.springbootlearning.domain.model.Customer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface CustomerRepository {

    List<Customer> findAll();

    void save(Customer customer);

    void saveAll(List<Customer> customers);


    void deleteById(Integer id);

    Customer getReferenceById(Integer id);

}
