package com.elhaddidy.springbootlearning.services;


import com.elhaddidy.springbootlearning.data.mappers.CustomerDAOtoCustomer;
import com.elhaddidy.springbootlearning.data.mappers.CustomerToCustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import com.elhaddidy.springbootlearning.exception.CustomerIsUnderAgeException;
import com.elhaddidy.springbootlearning.exception.CustomerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDAOtoCustomer customerDAOtoCustomer;
    private final CustomerToCustomerDAO customerToCustomerDAO;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        customerDAOtoCustomer = new CustomerDAOtoCustomer();
        customerToCustomerDAO = new CustomerToCustomerDAO();
    }

    public List<Customer> getCustomers() {
        return customerRepository
                .findAll().stream()
                .map(customerDAOtoCustomer)
                .collect(Collectors.toList());
    }

    public Customer getCustomer(Integer id) {

        try {
            return customerDAOtoCustomer.apply(
                    customerRepository.getReferenceById(id)
            );
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException(e.getMessage());
        }

    }

    public String addCustomer(Customer customer) {

        if (customer.getAge() >= 18) {
            customerRepository.save(
                    customerToCustomerDAO.apply(customer)
            );
        } else {
            throw new CustomerIsUnderAgeException("Customer is underage");
        }
        return "Success";
    }

    public String addCustomers(List<Customer> customers) {

        //  improve-> collect all underage customers name and return

        customers.forEach(c -> {
                    if (c.getAge() < 18) {
                        throw new CustomerIsUnderAgeException("One of the customers is underage");
                    }
                }
        );

        customerRepository.saveAll(customers
                .stream()
                .map(customerToCustomerDAO)
                .collect(Collectors.toList()));
        return "Success";
    }


    public String deleteCustomer(Integer id) {

        try {
            customerRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException("There is not customer with this id to be deleted");
        }


        customerRepository.deleteById(id);
        return "Success";
    }

    public String updateCustomerEmail(Integer id, String email) {

        Customer customer = customerDAOtoCustomer.apply(
                customerRepository.getReferenceById(id)
        );
        customer.setEmail(email);

        // bl

        customerRepository.save(customerToCustomerDAO.apply(customer));
        return "Success";
    }

    public String updateCustomer(Customer customer) {

        // bl

        Customer customerInDB = customerDAOtoCustomer.apply(
                customerRepository.getReferenceById(customer.getId())
        );
        customerInDB.setEmail(customer.getEmail());
        customerInDB.setAge(customer.getAge());
        customerInDB.setName(customer.getName());

        customerRepository.save(
                customerToCustomerDAO.apply(customerInDB)
        );
        return "Success";
    }


}
