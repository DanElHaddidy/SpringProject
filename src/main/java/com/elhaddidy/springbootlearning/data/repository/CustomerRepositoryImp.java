package com.elhaddidy.springbootlearning.data.repository;

import com.elhaddidy.springbootlearning.data.dao.CustomerDAO;
import com.elhaddidy.springbootlearning.data.mappers.CustomerDAOtoCustomer;
import com.elhaddidy.springbootlearning.data.mappers.CustomerToCustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomerRepositoryImp implements CustomerRepository {

    private final JpaR jpaR;
    private final CustomerDAOtoCustomer customerDAOtoCustomer;
    private final CustomerToCustomerDAO customerToCustomerDAO;

    public CustomerRepositoryImp(JpaR jpaR, CustomerDAOtoCustomer customerDAOtoCustomer, CustomerToCustomerDAO customerToCustomerDAO) {
        this.jpaR = jpaR;
        this.customerDAOtoCustomer = customerDAOtoCustomer;
        this.customerToCustomerDAO = customerToCustomerDAO;
    }

    @Override
    public List<Customer> findAll() {
        return jpaR
                .findAll()
                .stream()
                .map(customerDAOtoCustomer)
                .collect(Collectors.toList());

    }

    @Override
    public void save(Customer customer) {
        jpaR.save(customerToCustomerDAO.apply(customer));
    }

    @Override
    public void saveAll(List<Customer> customers) {
        jpaR.saveAll(customers
                .stream()
                .map(customerToCustomerDAO)
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteById(Integer id) {
        jpaR.deleteById(id);
    }

    @Override
    public Customer getReferenceById(Integer id) {
        return customerDAOtoCustomer.apply(jpaR.getReferenceById(id));
    }


}
@Component
interface
JpaR extends JpaRepository<CustomerDAO,Integer> {}