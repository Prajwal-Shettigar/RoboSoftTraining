package com.prajwal.employee.Controllers;


import com.prajwal.employee.Modules.Employee;
import com.prajwal.employee.Services.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    MyDao myDao;

    //to insert an employee into the database
    @PostMapping("/Employee")
    public String insertEmployee(@RequestBody Employee employee){
        return successOrFailure(myDao.insertAnEmployee(employee));
    }


    //to get all employees from database
    @GetMapping("/Employee")
    public List<Employee> getAllEmployees(){
        String selectStatement = "select * from employee";
        return myDao.getAllEmployees(selectStatement);
    }


    //to delete employee based on id
    @DeleteMapping("/Employee/{id}")
    public String deleteEmployee(@PathVariable int id){
        return successOrFailure(myDao.deleteEmployee(id));
    }

    //updates employee based on id
    @PutMapping("/Employee/{id}")
    public String updateEmployee(@PathVariable int id,@RequestBody Employee employee){
        return successOrFailure(myDao.updateEmployee(id,employee));
    }

    //get employee based on id
    @GetMapping("/Employee/{id}")
    public Employee getEmployeeById(@PathVariable int id){
        return myDao.getEmployee(id).get(0);
    }




    //to print a string as success or failure based on whether result is greater than zero or less than
    public String successOrFailure(int status){
        if(status>0){
            return "Success";
        }

        return "Failure";
    }
}
