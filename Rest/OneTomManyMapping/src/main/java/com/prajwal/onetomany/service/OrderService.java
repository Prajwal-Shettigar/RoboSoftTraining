package com.prajwal.onetomany.service;


import com.prajwal.onetomany.entity.Customer;
import com.prajwal.onetomany.repository.CustomerRepository;
import com.prajwal.onetomany.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;


    //save customer
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    //get all customers
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
