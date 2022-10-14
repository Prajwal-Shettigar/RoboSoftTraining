package com.prajwal.hospital.model;
/*
{
"maxPatients":3,
"feePerDay":400.00
}
 */
public class Ward {

    private int id;
    private int maxPatients;
    private double feePerDay;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxPatients() {
        return maxPatients;
    }

    public void setMaxPatients(int maxPatients) {
        this.maxPatients = maxPatients;
    }

    public double getFeePerDay() {
        return feePerDay;
    }

    public void setFeePerDay(double feePerDay) {
        this.feePerDay = feePerDay;
    }
}
