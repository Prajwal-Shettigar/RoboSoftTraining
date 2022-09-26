package com.prajwal.insurance.model;

/*
{
"driverId":"RT005",
"name":"prajwal",
"address":"udupi"
}
 */
public class Person {

    private String driverId;
    private String name;
    private String address;


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
