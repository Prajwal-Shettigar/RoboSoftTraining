package com.prajwal.employee.Service;


import com.prajwal.employee.Module.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServicesImpl implements EmployeeServices{


    private List<Employee> employeeList;


    public EmployeeServicesImpl() {

        employeeList = new ArrayList<>();

        employeeList.add(new Employee(1,"prajwal","java","udupi"));
        employeeList.add(new Employee(2,"employee2","flutter","Karkala"));
        employeeList.add(new Employee(3,"employee3","NodeJS","Managalore"));
        employeeList.add(new Employee(4,"employee4","IOS","Moodbidri"));

    }

    //get all employees
    @Override
    public List<Employee> obtainAllEmployees() {
        return employeeList;
    }


//    get employee by id
    @Override
    public Employee getEmployeeById(int id) {
       return employeeList.stream().filter(employee -> employee.getEmployeeId() == id).findAny().orElse(null);
    }


//    remove employee by id reutrn true on successful deletion and false if employee not found
    @Override
    public boolean deleteEmployeeById(int id) {
        Employee employee = getEmployeeById(id);

        if(employee!=null){
            employeeList.remove(employee);
            return  true;

        }

        return false;
    }


//    add employee only if employee by same id not present in the list
    @Override
    public boolean addEmployee(Employee employee) {
        if(getEmployeeById(employee.getEmployeeId())==null){
            employeeList.add(employee);
            return true;
        }

        return false;
    }



    //update the employee using his id
    @Override
    public boolean updateEmployee(int id, Employee employee) {
        Employee employee1 = getEmployeeById(id);

        if(employee1!=null){

            //update the name of employee
            if(employee.getEmployeeName()!=null){
                employee1.setEmployeeName(employee.getEmployeeName());
            }

            //update the department of employee
            if(employee.getDepartment()!=null){
                employee1.setDepartment(employee.getDepartment());
            }

            //update the location of the employee
            if(employee.getLocation()!=null){
                employee1.setLocation(employee.getLocation());
            }

            return true;
        }

        return false;
    }
}
