package com.prajwal.productjpa.service;

import com.prajwal.productjpa.model.Employee;
import com.prajwal.productjpa.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    //finds all the employees
    @Override
    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }


    //finds employee by id if not present returns null
    @Override
    public Employee findEmployeeById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }


    //adds an employee and returns the details of added employee if already present returns null
    @Override
    public Employee addEmployee(Employee employee) {
        if(findEmployeeById(employee.getId())!=null){
            return null;
        }
        return employeeRepository.save(employee);
    }


    //deletes all employees in the data base and returns a string after deletion
    @Override
    public String deleteAllData() {
        employeeRepository.deleteAll();
        return "all records deleted succesfully..";
    }



    //delete an employee based on id if not present
    @Override
    public String deleteEmployeeById(long id) {
        if(findEmployeeById(id)==null){
            return "employee by the id "+id+"not present in our database..";
        }
        employeeRepository.deleteById(id);
        return "employee deleted successfully..";

    }

    //find employees based on name
    public List<Employee> findEmployeeByName(String name){
        return employeeRepository.findByName(name);
    }


    //update employee based on id
    @Override
    public String updateEmplyee(long id, Employee employee) {

        Employee updatableEmployee=findEmployeeById(id);
       if(updatableEmployee==null){
           return "employee does not exists in the database";
       }

       if(employee.getName()!=null){
           updatableEmployee.setName(employee.getName());
       }

       if(employee.getCity()!=null){
           updatableEmployee.setCity(employee.getCity());
       }

       employeeRepository.save(updatableEmployee);

       return "employee data updated successfully.....";
    }


    //delete employees using name
    @Override
    public String deleteByName(String name) {

        List<Employee> employees = findEmployeeByName(name);

        if(employees.size()<1){
            return "no records available with name \'"+name+"\' in the database....";
        }

        employeeRepository.deleteAll(employees);

        return "records deleted successfullt";
    }


    //delete based on city
    @Override
    public String deleteByCity(String city) {
        employeeRepository.deleteByCity(city);
        return "records with city : \'"+city+"\' deleted successfully..";
    }


    //find employees by city

    @Override
    public List<Employee> findEmployeeByCity(String city) {
        return employeeRepository.findByCity(city);
    }


    //get based on name and city
    @Override
    public List<Employee> getByNameAndCity(String name, String city) {
        return employeeRepository.findByNameandCity(name,city);

    }
}
