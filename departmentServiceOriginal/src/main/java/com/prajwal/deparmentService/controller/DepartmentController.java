package com.prajwal.deparmentService.controller;


import com.prajwal.deparmentService.entity.Department;
import com.prajwal.deparmentService.service.DeparmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Department")
public class DepartmentController {

    @Autowired
    DeparmentService deparmentService;

    //get all departments
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return deparmentService.getAllDepartments();

    }

    //get department by id
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable("id") long id){
        return deparmentService.getDepartmentById(id);
    }

    //save a department
    @PostMapping("/saveDepartment")
    public Department saveDepartment(@RequestBody Department department){
        return deparmentService.saveDepartment(department);
    }
}
