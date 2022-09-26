package com.prajwal.onetoone.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
{
"name":"prajwal",
"employeeInfo":{
"gender":"male",
"address":"udupi"
}
}
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {


    @Id
    @GeneratedValue
    private int id;
    private String name;


    //create a foreign key column named emp_info_id in EmployeeInfo table which references id column of Employee table
    @OneToOne(targetEntity = EmployeeInfo.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "emp_info_id",referencedColumnName = "employeeInfoId")
    EmployeeInfo employeeInfo;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }
}
