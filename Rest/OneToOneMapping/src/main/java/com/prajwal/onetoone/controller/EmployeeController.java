package com.prajwal.onetoone.controller;


import com.prajwal.onetoone.entity.Employee;
import com.prajwal.onetoone.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @PutMapping("/Employee")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }


    @GetMapping("/Employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
