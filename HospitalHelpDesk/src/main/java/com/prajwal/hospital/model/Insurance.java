package com.prajwal.hospital.model;


/*
{
"name":"prajwal",
"age":21,
"gender":"male",
"claimAmount":200000.00
}
 */
public class Insurance {

    private int id;
    private String name;
    private int age;
    private String gender;
    private double claimAmount;

    public Insurance() {
    }

    public Insurance(String name, int age, String gender,double claimAmount) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.claimAmount = claimAmount;
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

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }
}
