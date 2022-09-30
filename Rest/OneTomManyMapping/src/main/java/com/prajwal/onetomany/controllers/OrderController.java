package com.prajwal.onetomany.controllers;

import com.prajwal.onetomany.entity.Customer;
import com.prajwal.onetomany.entity.Product;
import com.prajwal.onetomany.repository.ProductRepository;
import com.prajwal.onetomany.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;


    @PutMapping("/Customer")
    public Customer putCustomer(@RequestBody Customer customer){
        return orderService.saveCustomer(customer);
    }

    @GetMapping("/Customers")
    public List<Customer> getAllCustomers(){
        return orderService.getAllCustomers();
    }

    @PutMapping("/Product")
    public Product addProduct(@RequestBody Product product){
        return productRepository.save(product);
    }
}
