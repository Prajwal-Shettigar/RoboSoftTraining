package com.prajwal.hospital.model;

public class Admission {

    private int patientId;
    private int wardId;
    private int noOfDays;
    private double feePerDay;
    private double totalFee;

    private double feeAfterClaim;


    public Admission(int patientId, int wardId, int noOfDays, double feePerDay, double totalFee, double feeAfterClaim) {
        this.patientId = patientId;
        this.wardId = wardId;
        this.noOfDays = noOfDays;
        this.feePerDay = feePerDay;
        this.totalFee = totalFee;
        this.feeAfterClaim = feeAfterClaim;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public double getFeePerDay() {
        return feePerDay;
    }

    public void setFeePerDay(double feePerDay) {
        this.feePerDay = feePerDay;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getFeeAfterClaim() {
        return feeAfterClaim;
    }

    public void setFeeAfterClaim(double feeAfterClaim) {
        this.feeAfterClaim = feeAfterClaim;
    }
}

