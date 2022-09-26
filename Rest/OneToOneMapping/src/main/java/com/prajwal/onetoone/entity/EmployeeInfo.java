package com.prajwal.onetoone.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeInfo {

    @Id
    @GeneratedValue
    private int employeeInfoId;
    private String gender;
    private String address;



    public int getEmployeeInfoId() {
        return employeeInfoId;
    }

    public void setEmployeeInfoId(int employeeInfoId) {
        this.employeeInfoId = employeeInfoId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
