package com.prajwal.manytomany.Controller;


import com.prajwal.manytomany.Model.Address;
import com.prajwal.manytomany.Model.Customer;
import com.prajwal.manytomany.Service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Customer")
public class MappingController {


    @Autowired
    MappingService mappingService;

    //add customer
    @PutMapping
    public Customer addCustomer(@RequestBody Customer customer){
        return mappingService.saveCustomer(customer);
    }


    //get all customers
    @GetMapping
    public List<Customer> getAllCustomers(){
        return mappingService.getAllcustomers();
    }

    //get customer by id
    @GetMapping("/id/{id}")
    public Customer getById(@PathVariable int id){
        return mappingService.getCustomerById(id);
    }

    //get customers by name
    @GetMapping("/name/{name}")
    public List<Customer> getByName(@PathVariable String name){
        return mappingService.getCustomerByName(name);
    }

    //get customers with match
    @GetMapping("/match/{subString}")
    public List<Customer> getMatch(@PathVariable String subString){
        return mappingService.getCustomerByMatch(subString);
    }


    //delete customer by id
    @DeleteMapping("/id/{id}")
    public String deleteById(@PathVariable int id){
        mappingService.deleteCusomerById(id);
        return "deleted successfully..";
    }

    //delete customer by name
    @DeleteMapping("/name/{name}")
    public String deleteCustomerByName(@PathVariable String name){
        mappingService.deleteCustomerByName(name);
        return "deleted successfully..";
    }

    //get all addresses
    @GetMapping("/Address")
    public List<Address> getAllAddresses(){
        return mappingService.getAllAddressses();
    }

    //get address by id
    @GetMapping("/Address/id/{id}")
    public Address getAddressById(@PathVariable int id){
        return mappingService.getAddressById(id);
    }



//    //add customer to addresses
//    @PutMapping("/CusAdd")
//    public void addCustomersIntoAddress(@RequestBody CusAdd cusAdd){
//        mappingService.addCustomersToAddress(cusAdd.getcIds(),cusAdd.getAdIds());
//    }
}
