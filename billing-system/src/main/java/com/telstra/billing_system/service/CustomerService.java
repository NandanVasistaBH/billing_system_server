package com.telstra.billing_system.service;

import com.telstra.billing_system.model.Customer;
import com.telstra.billing_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setName(customerDetails.getName());
            customer.setCustEmail(customerDetails.getCustEmail());
            customer.setCustPhoneNo(customerDetails.getCustPhoneNo());
            customer.setSupplier(customerDetails.getSupplier());
            customer.setCustPassword(customerDetails.getCustPassword());
            return customerRepository.save(customer);
        }
        return null;
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
