package com.prajwal.insurance.model;

/*
{
"regNo":"KA20PA007",
"model":"sedan",
"year":1985
}
 */
public class Car {

    private String regNo;
    private String model;
    private int year;

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
