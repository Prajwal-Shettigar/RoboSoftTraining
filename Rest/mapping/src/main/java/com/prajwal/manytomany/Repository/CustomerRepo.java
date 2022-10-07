package com.prajwal.manytomany.Repository;

import com.prajwal.manytomany.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {


    //get by name
    List<Customer> findByName(String name);

    //get by match
    List<Customer> findByNameContaining(String subString);

    //delete by name
    void deleteByName(String name);
}
