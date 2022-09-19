package com.prajwal.productjpa.service;

import com.prajwal.productjpa.model.Employee;

import java.util.List;

public interface EmployeeService {



    //finds all the employees
    List<Employee> findAllEmployee();


    //finds employee by id if not present returns null
    Employee findEmployeeById(long id);


    //adds an employee and returns the details of added employee
    Employee addEmployee(Employee employee);


    //deletes all employees in the data base and returns a string after deletion
    String deleteAllData();


    //delete an employee based on the id
    String deleteEmployeeById(long id);


    //find employee based on name
    List<Employee> findEmployeeByName(String name);

    //update employee based on id
    String updateEmplyee(long id,Employee employee);

    //delete employee using name
    String deleteByName(String name);


    //delete based on city
    String deleteByCity(String city);

    //find employees by city
    List<Employee> findEmployeeByCity(String city);


    //get by name and city
    List<Employee> getByNameAndCity(String name,String city);

}
