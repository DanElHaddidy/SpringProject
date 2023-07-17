package com.elhaddidy.springbootlearning.domain.repository;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO,Integer> {

    List<Customer> findByName(String name);

}
