package com.prajwal.productjpa.controller;


import com.prajwal.productjpa.model.Employee;
import com.prajwal.productjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService service;

    //get all employees
    @GetMapping("/Employees")
    public List<Employee> getAllEmployees(){
        return service.findAllEmployee();
    }

    //get employee based on id
    @GetMapping("/Employee/{id}")
    public Employee getEmployeeById(@PathVariable long id){
        return service.findEmployeeById(id);
    }

    //add an employee
    @PutMapping("/Employee")
    public Employee addEmployee(@RequestBody Employee employee){
        return service.addEmployee(employee);
    }

    //delete all employees
    @DeleteMapping("/Employees")
    public String deleteAllEmployees(){
        return service.deleteAllData();
    }

    //delete employee based on id
    @DeleteMapping("/Employee/{id}")
    public String deleteEmployeeById(@PathVariable long id){
        return service.deleteEmployeeById(id);
    }

    //get employees based on name
    @GetMapping(value = "/Employees/{name}")
    public List<Employee> getEmployeeByName(@PathVariable String name){
        return service.findEmployeeByName(name);
    }

    //update employee using id
    @PatchMapping("/Employee/{id}")
    public String updateEmployeeById(@PathVariable long id,@RequestBody Employee employee){
        return service.updateEmplyee(id,employee);
    }

    //delete employees based on name
    @DeleteMapping("/Employees/{name}")
    public String deleteEmployeeByName(@PathVariable String name){
        return service.deleteByName(name);
    }

    //delete by city
    @DeleteMapping("/Employees/city/{city}")
    public String deleteEmployeesByCity(@PathVariable String city){
        return service.deleteByCity(city);
    }

    //find employees based on city
    @GetMapping("/Employees/city/{city}")
    public List<Employee> getEmployeeUsingCity(@PathVariable String city){
        return service.findEmployeeByCity(city);
    }

    //get based on name and city
    @GetMapping("/Employees/namecity/{name}/{city}")
    public List<Employee> getByNameAndCity(@PathVariable String name,@PathVariable String city){
        return service.getByNameAndCity(name,city);
    }

}
