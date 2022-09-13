package com.prajwal.employee.Service;

import com.prajwal.employee.Module.Employee;

import java.util.List;

public interface EmployeeServices {


//    to get all employees
    public List<Employee> obtainAllEmployees();


//    to get employee by id
    public Employee getEmployeeById(int id);

// delete employee by id
    public boolean deleteEmployeeById(int id);

//    add employee into the list
    public boolean addEmployee(Employee employee);


//    update employee using id
    public boolean updateEmployee(int id,Employee employee);
}
