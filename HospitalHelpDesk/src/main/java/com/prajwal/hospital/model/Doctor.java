package com.prajwal.hospital.model;

/*
{
"name":"prajwal",
"department":"dep1",
"maxPatients":"3",
"fee":300.00,
"room_number":2
}
 */

public class Doctor {

    private int id;
    private String name;
    private String department;
    private int maxPatients;
    private double fee;
    private int room_number;

    public Doctor() {
    }

    public Doctor(String name, String department, int maxPatients, double fee, int room_number) {
        this.name = name;
        this.department = department;
        this.maxPatients = maxPatients;
        this.fee = fee;
        this.room_number = room_number;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }
}
