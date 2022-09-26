package com.prajwal.onetomany.controllers;

import com.prajwal.onetomany.entity.Customer;
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


    @PutMapping("/Customer")
    public Customer putCustomer(@RequestBody Customer customer){
        return orderService.saveCustomer(customer);
    }

    @GetMapping("/Customers")
    public List<Customer> getAllCustomers(){
        return orderService.getAllCustomers();
    }
}
