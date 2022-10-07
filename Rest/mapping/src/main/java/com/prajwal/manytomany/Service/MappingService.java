package com.prajwal.manytomany.Service;


import com.prajwal.manytomany.Model.Address;
import com.prajwal.manytomany.Model.Customer;
import com.prajwal.manytomany.Repository.AddressRepo;
import com.prajwal.manytomany.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingService {


    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AddressRepo addressRepo;


    //save customer
    public Customer saveCustomer(Customer customer){
        return customerRepo.save(customer);
    }


//    //save address
//    public Address saveAddress(Address address){
//        return addressRepo.save(address);
//    }


    //get all customers
    public List<Customer> getAllcustomers(){
        return customerRepo.findAll();
    }

    //get customer by id
    public Customer getCustomerById(int id){
        return customerRepo.findById(id).orElse(null);
    }

    //get by name
    public List<Customer> getCustomerByName(String name){
        return customerRepo.findByName(name);
    }

    //get customer by name match
    public List<Customer> getCustomerByMatch(String subString){
        return customerRepo.findByNameContaining(subString);
    }

    //delete customer with id
    public void deleteCusomerById(int id){
        customerRepo.deleteById(id);
    }

    //delete by name
    public void deleteCustomerByName(String name){
        customerRepo.deleteByName(name);
    }

    //get all addresses
    public List<Address> getAllAddressses(){
        return addressRepo.findAll();
    }

    //get address by id
    public Address getAddressById(int id){
        return addressRepo.findById(id).orElse(null);
    }





//    //add customers to a address
//    public void addCustomersToAddress(List<Integer> cutomerIds,List<Integer> addressIds){
//        Set<Customer> customers = new HashSet<>();
//
//        for(int cId : cutomerIds){
//            customers.add(customerRepo.findById(cId).orElse(null));
//        }
//
//        List<Address> address = new ArrayList<>();
//        for(int aId : addressIds){
//            address.add(addressRepo.findById(aId).orElse(null));
//        }
//
//        for(Address adrs:address){
//            if(adrs!=null){
//                adrs.setCustomers(customers);
//            }
//        }
//
//        System.out.println(addressRepo.saveAll(address));
//    }

}
