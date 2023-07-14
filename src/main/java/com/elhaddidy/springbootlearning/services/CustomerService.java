package com.elhaddidy.springbootlearning.services;


import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getCustomers(){
    return customerRepository.findAll();
  }

  public Customer getCustomer(Integer id){
    return customerRepository.getReferenceById(id);
  }

  public void addCustomer(Customer customer){
    customerRepository.save(customer);
  }

  public void addCustomers(List<Customer> customers) {
    customerRepository.saveAll(customers);
  }


  public void deleteCustomer(Integer id) {
  // bl
    customerRepository.deleteById(id);
  }

  public void updateCustomerEmail(Integer id, String email) {

    Customer customer = customerRepository.getReferenceById(id);
    customer.setEmail(email);
    // bl

    customerRepository.save(customer);

  }

  public void updateCustomer(Customer customer) {



    // bl

    Customer customerInDB = customerRepository.getReferenceById(customer.getId());
    customerInDB.setEmail(customer.getEmail());
    customerInDB.setAge(customer.getAge());
    customerInDB.setName(customer.getName());

    customerRepository.save(customerInDB);

  }


}
