package com.elhaddidy.springbootlearning.domain.repository;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerDAO,Integer> {

    boolean existsCustomerDAOById(Integer id);
    boolean existsCustomerDAOByName(String name);
    boolean existsCustomerDAOByEmail(String email);


}
