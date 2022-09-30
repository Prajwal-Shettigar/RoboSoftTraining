package com.prajwal.jpql.service;

import com.prajwal.jpql.entity.Employee;
import com.prajwal.jpql.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    //insert into employee
    public Employee insertEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    //get employee name based on id
    public String getName(int eid){
        return employeeRepo.getNameById(eid);
    }

    //fetch users by name
    public List<Employee> getEmployeesByName(String name){
        return employeeRepo.getEmployeesByName(name);
    }


    //get employees by designation
    public List<Employee> getEmployeeByDesignation(String desig){
        return employeeRepo.getEmployeesByDesignation(desig);
    }
}
