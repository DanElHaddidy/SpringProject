package com.elhaddidy.springbootlearning.controllers;


import com.elhaddidy.springbootlearning.custom_response.ResponseHandler;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {


    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    @GetMapping({"{customerId}"})
    public ResponseEntity<Object> getCustomer(@PathVariable("customerId") Integer id){
        return ResponseHandler.responseBuilder(
                "Requested customer details",
                HttpStatus.OK,
                customerService.getCustomer(id));
    }

    @PostMapping
    public void addCustomer(@RequestBody Customer customer){
        customerService.addCustomer(customer);
    }

    @PostMapping("list")
    public void addCustomers(@RequestBody List<Customer> customers){
        customerService.addCustomers(customers);
    }

    @DeleteMapping({"{customerId}"})
    public void deleteCustomer (@PathVariable("customerId") Integer id){
        customerService.deleteCustomer(id);
    }



    @PatchMapping({"{customerId}"})
    public void updateCustomerEmail (@PathVariable("customerId") Integer id,
                                     @RequestBody Customer customer){
        customerService.updateCustomerEmail(id,customer.getEmail());
    }


    @PutMapping({"{customerId}"})
    public void updateCustomer (@PathVariable("customerId") Integer id,
                                @RequestBody Customer customer){
        customer.setId(id);
        customerService.updateCustomer(customer);
    }
}
