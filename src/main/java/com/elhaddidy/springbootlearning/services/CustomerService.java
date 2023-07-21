package com.elhaddidy.springbootlearning.services;


import com.elhaddidy.springbootlearning.data.mappers.CustomerDAOtoCustomer;
import com.elhaddidy.springbootlearning.data.mappers.CustomerToCustomerDAO;
import com.elhaddidy.springbootlearning.domain.model.Customer;
import com.elhaddidy.springbootlearning.domain.repository.CustomerRepository;
import com.elhaddidy.springbootlearning.exception.CustomerIsUnderAgeException;
import com.elhaddidy.springbootlearning.exception.CustomerNotFoundException;
import com.elhaddidy.springbootlearning.exception.DuplicateResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final CustomerDAOtoCustomer customerDAOtoCustomer;
    private final CustomerToCustomerDAO customerToCustomerDAO;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        customerDAOtoCustomer = new CustomerDAOtoCustomer();
        customerToCustomerDAO = new CustomerToCustomerDAO();
    }


    /**
     * Calls DB to get all customers from DB.
     * @return List of all customers from DB.
     */
    public List<Customer> getCustomers() {
        logger.info("Getting all customers form DB");

        return customerRepository
                .findAll().stream()
                .map(customerDAOtoCustomer)
                .toList();
    }

    /**
     * Checks if customer id is in DB.
     * Calls DB to get customer by id.
     * @param id
     * @return Customer from DB based on id
     */
    public Customer getCustomer(Integer id) {
        logger.info("Getting customer form DB by id: " + id);

        if(customerRepository.existsCustomerDAOById(id)) {
            throw new CustomerNotFoundException(
                    "There is not customer with %d id to be deleted".formatted(id)
            );
        }

        return customerDAOtoCustomer.apply(
                    customerRepository.getReferenceById(id)
            );

    }

    /**
     *   Checks for name & email duplicity.
     *   Checks if age > 18.
     *   Saves customer to DB.
     * @param customer Simple POJO class
     */
    public void addCustomer(Customer customer) {
        logger.info(
                "Adding customer to DB with name: %s email? %s ".formatted(
                        customer.getName(),
                        customer.getEmail()
                )
        );

        if (customerRepository.existsCustomerDAOByName(customer.getName())) {
            throw new DuplicateResourceException(
                    "Name %s is already taken".formatted(customer.getName())
            );
        } else if (customerRepository.existsCustomerDAOByEmail(customer.getEmail())) {
            throw new DuplicateResourceException(
                    "Email %s is already taken".formatted(customer.getEmail())
            );
        } else if (customer.getAge() < 18) {
            throw new CustomerIsUnderAgeException(customer.getName()+" is underage");
        } else {
            customerRepository.save(
                    customerToCustomerDAO.apply(customer)
            );
        }
    }

    /**
     * Checks if all customers are above age 18.
     * Then saves all customers to DB.
     * @param customers Simple list of POJO classes
     */
    public void addCustomers(List<Customer> customers) {
        logger.info("Adding customers to DB");

        List<String> underAgeCustomers = customers
                .stream()
                .filter(customer -> customer.getAge() < 18)
                .map(customer -> customer.name)
                .toList();

        if (!underAgeCustomers.isEmpty()) {
            throw new CustomerIsUnderAgeException("Customer%s %s %s underage".formatted(
                    (underAgeCustomers.size() == 1) ? "" : "s",
                    underAgeCustomers.toString(),
                    (underAgeCustomers.size() == 1) ? "is" : "are")
            );
        }

        customerRepository.saveAll(customers
                .stream()
                .map(customerToCustomerDAO)
                .collect(Collectors.toList()));

    }

    /**
     * Checks if customer id is in DB.
     * Then deletes customer from DB.
     * @param id
     */
    public void deleteCustomer(Integer id) {
        logger.info("Deleting customer with id: " + id);

        if(customerRepository.existsCustomerDAOById(id)) {
            throw new CustomerNotFoundException(
                    "There is not customer with %d id to be deleted".formatted(id)
            );
        }
        customerRepository.deleteById(id);

    }


    /**
     *  Checks if customer id is in DB.
     *  Checks for email duplicity.
     *  Then updates DB.
     * @param id
     * @param email
     */
    public void updateCustomerEmail(Integer id, String email) {
        logger.info("Updating customer with id: %d email: %s".formatted(id, email));

        if (customerRepository.existsCustomerDAOById(id)) {
            throw new CustomerNotFoundException(
                    "There is not customer with %d id to be updated".formatted(id)
            );
        } else if (customerRepository.existsCustomerDAOByEmail(email)) {
            throw new DuplicateResourceException(
                    "Email %s is already taken".formatted(email)
            );
        }

        Customer customer = customerDAOtoCustomer.apply(
                customerRepository.getReferenceById(id)
        );

        customer.setEmail(email);
        customerRepository.save(customerToCustomerDAO.apply(customer));

    }

    /**
     *  Checks if customer id is in DB.
     *  Checks for name & email duplicity.
     *  Checks if age > 18.
     *  Then updates DB.
     * @param customer Simple POJO class
     */
    public void updateCustomer(Customer customer) {
        logger.info("Updating customer with name " + customer.getName());

        if (customerRepository.existsCustomerDAOById(customer.getId())) {
            throw new CustomerNotFoundException(
                    "There is not customer with %d id to be updated".formatted(customer.getId())
            );
        } else if (customerRepository.existsCustomerDAOByName(customer.getName())) {
            throw new DuplicateResourceException(
                    "Name %s is already taken".formatted(customer.getName())
            );
        } else if (customerRepository.existsCustomerDAOByEmail(customer.getEmail())) {
            throw new DuplicateResourceException(
                    "Email %s is already taken".formatted(customer.getEmail())
            );
        } else if (customer.getAge() < 18) {
            throw new CustomerIsUnderAgeException("Customer is underage");
        }

        Customer customerInDB = customerDAOtoCustomer.apply(
                customerRepository.getReferenceById(customer.getId())
        );
        customerInDB.setEmail(customer.getEmail());
        customerInDB.setAge(customer.getAge());
        customerInDB.setName(customer.getName());

        customerRepository.save(
                customerToCustomerDAO.apply(customerInDB)
        );

    }

}
