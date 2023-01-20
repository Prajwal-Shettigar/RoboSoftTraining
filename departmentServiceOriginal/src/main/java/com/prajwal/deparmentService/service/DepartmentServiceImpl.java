package com.prajwal.deparmentService.service;


import com.prajwal.deparmentService.entity.Department;
import com.prajwal.deparmentService.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DeparmentService{


    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }


    @Override
    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
