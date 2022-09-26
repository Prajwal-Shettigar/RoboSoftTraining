package com.prajwal.insurance.model;


/*
{
"driverId":"",
"regNo":"KA-12-1",
"reportNumber":1,
"damageAmount":4000
}
 */
public class Participated {

    private String driverId;
    private String regNo;
    private int reportNumber;
    private int damageAmount;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public int getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(int damageAmount) {
        this.damageAmount = damageAmount;
    }
}
