package com.prajwal.jpql.controller;


import com.prajwal.jpql.entity.Employee;
import com.prajwal.jpql.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    //add new employee
    @PutMapping("/Add")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.insertEmployee(employee);
    }

    //get name based on id
    @GetMapping("/Name/{id}")
    public String getNameById(@PathVariable int id){
        return employeeService.getName(id);
    }

    //get employee by name
    @GetMapping("/Employees/Name/{name}")
    public List<Employee> getEmployeesByName(@PathVariable String name){
        return employeeService.getEmployeesByName(name);
    }

    //get employees by designation
    @GetMapping("/Employees/Desig/{designation}")
    public List<Employee> getEmployeeByDesignation(@PathVariable String designation){
        return employeeService.getEmployeeByDesignation(designation);
    }
}
