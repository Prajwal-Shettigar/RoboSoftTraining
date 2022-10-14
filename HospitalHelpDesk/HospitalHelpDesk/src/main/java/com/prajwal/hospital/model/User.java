package com.prajwal.hospital.model;

import java.math.BigDecimal;
/*{
        "name":"prajwal",
        "age":21,
        "gender":"male",
        "telNumber":7619376175,
        "insuranceId":2
        }
 */
public class User {

    private int id;
    private String name;
    private int age;
    private String gender;
    private BigDecimal telNumber;
    private int insuranceId;

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
}
