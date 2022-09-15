package com.prajwal.employee.Module;




//employee pojo class
public class Employee {

    private int employeeId;

    private String employeeName;

    private String department;

    private String location;


    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String department, String location) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.location = location;
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", department='" + department + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
