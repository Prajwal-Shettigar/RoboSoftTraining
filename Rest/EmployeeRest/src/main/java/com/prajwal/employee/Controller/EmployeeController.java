package com.prajwal.employee.Controller;


import com.prajwal.employee.Module.Employee;
import com.prajwal.employee.Service.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    //gets all employees
    @GetMapping("/Employees")
    public List<Employee> getEmployees(){
        return employeeServices.obtainAllEmployees();
    }


    //get employee by id
    @GetMapping("/Employees/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return employeeServices.getEmployeeById(id);
    }

    //delete employee using id
    @DeleteMapping("/Employees/{id}")
    public String deleteEmployeeById(@PathVariable int id){
        return returnSuccesfulOrNot(employeeServices.deleteEmployeeById(id));
    }

    //add employee
    @PostMapping("/ADD")
    public String addEmployee(@RequestBody Employee employee){
        return returnSuccesfulOrNot(employeeServices.addEmployee(employee));
    }

    //update employee based on id
    @PutMapping("Employees/{id}")
    public String updateEmployee(@PathVariable int id,@RequestBody Employee employee){
        return returnSuccesfulOrNot(employeeServices.updateEmployee(id,employee));
    }




    //returns a string bases on whether the operation was successful or not
    public String returnSuccesfulOrNot(Boolean success){
        if(success){
            return "Successful Operaation";
        }

        return "Unsuccessful Operation";
    }
}
