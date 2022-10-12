package com.prajwal.hospital.model;

/*
{
"name":"prajwal",
"age":21,
"gender":"male",
"type":1,
"telNumber":7619376175,
"insuranceId":2,
"numberOfDays":2
}
 */

import java.math.BigDecimal;

public class Patient {

    private int id;
    private String name;
    private int age;
    private String gender;
    private String type;
    private BigDecimal telNumber;
    private int insuranceId;
    private int numberOfDays;

    public Patient() {
    }

    public Patient(String name, int age, String gender, String type, BigDecimal telNumber, int insuranceId, int numberOfDays) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.telNumber = telNumber;
        this.insuranceId = insuranceId;
        this.numberOfDays = numberOfDays;
    }

    public Patient(String name, int age, String gender, String type, BigDecimal telNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.telNumber = telNumber;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(BigDecimal telNumber) {
        this.telNumber = telNumber;
    }

    public int getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId) {
        this.insuranceId = insuranceId;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
