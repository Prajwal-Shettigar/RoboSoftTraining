package com.prajwal.employee.Controller;


import com.prajwal.employee.Module.Employee;
import com.prajwal.employee.Service.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    //gets all employees if no found return 404 status
    @GetMapping("/Employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeServices.obtainAllEmployees();

        if (employees.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.of(Optional.of(employees));
    }


    //get employee by id if not found 404 status code
    @GetMapping("/Employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        Employee employee = employeeServices.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.of(Optional.of(employee));
    }

    //delete employee using id
    @DeleteMapping("/Employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable int id) {
        return returnSuccesfulOrNot(employeeServices.deleteEmployeeById(id));
    }

    //add employee
    @PostMapping("/Employees")
    public ResponseEntity<Void> addEmployee(@RequestBody Employee employee) {
        return returnSuccesfulOrNot(employeeServices.addEmployee(employee));
    }

    //update employee based on id
    @PatchMapping("/Employees/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        System.out.println(employee);
        return returnSuccesfulOrNot(employeeServices.updateEmployee(id, employee));
    }


    //returns a string based on whether the operation was successful or not
    public ResponseEntity<Void> returnSuccesfulOrNot(Boolean success) {
        if (success) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
