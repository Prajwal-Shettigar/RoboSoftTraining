package com.prajwal.deparmentService.service;

import com.prajwal.deparmentService.entity.Department;

import java.util.List;

public interface DeparmentService {
    List<Department> getAllDepartments();

    Department getDepartmentById(long id);

    Department saveDepartment(Department department);
}
