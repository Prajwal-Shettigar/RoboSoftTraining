package com.prajwal.employee.Controllers;


import com.prajwal.employee.Modules.Employee;
import com.prajwal.employee.Services.MyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {



    @Autowired
    MyDao myDao;

    //to insert an employee into the database
    @PostMapping("/Employee")
    public ResponseEntity<Void> insertEmployee(@RequestBody Employee employee){
        return successOrFailure(myDao.insertAnEmployee(employee));
    }


    //to get all employees from database
    @GetMapping(value="/Employee")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        String selectStatement = "select * from employee";
        List<Employee> employees =  myDao.getAllEmployees(selectStatement);

        if(employees.size()<1){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(employees));
    }


    //to delete employee based on id
    @DeleteMapping("/Employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id){
        return successOrFailure(myDao.deleteEmployee(id));
    }

    //updates employee based on id but using patch method
    @PatchMapping(value="/Employee/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable int id, @RequestBody Employee employee){
        return successOrFailure(myDao.updateEmployee(id,employee));
    }


    //also updates employee details by id but using put method
    @PutMapping(value="/Employee/{id}")
    public ResponseEntity<Void> updateEmployeee(@PathVariable int id, @RequestBody Employee employee){
        return successOrFailure(myDao.updateEmployee(id,employee));
    }


    //get employee based on id
    @GetMapping("/Employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id){
        Employee employee =  myDao.getEmployeeById(id);

        if(employee == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.of(Optional.of(employee));
    }



    //delete all employees from table
    @DeleteMapping("/Employee")
    public ResponseEntity<Void> deleteAllEmployees(){
        return successOrFailure(myDao.deleteAll());
    }




    //to print a string as success or failure based on whether result is greater than zero or less than
    public ResponseEntity<Void> successOrFailure(int status){
        if(status>0){
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
