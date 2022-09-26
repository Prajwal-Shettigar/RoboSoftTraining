package com.prajwal.insurance.model;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/*
{
"reportNumber":201,
"accidentDate":"2012-05-31",
"location":"udupi",
"participents":[{
    "driverId":"RT005",
    "regNo":"KA-12-1",
    "reportNumber":1,
    "damageAmount":4000
    }]
}
 */
public class Accident {

    private int reportNumber;
    private Date accidentDate;
    private String location;

    private List<Participated> participents;

    public Accident() {
        participents = new ArrayList<>();
    }

    public void setAccidentDate(Date accidentDate) {
        this.accidentDate = accidentDate;
    }

    public List<Participated> getParticipents() {
        return participents;
    }

    public void setParticipents(List<Participated> participents) {
        this.participents = participents;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Date getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(String accidentDate) {
        this.accidentDate = Date.valueOf(accidentDate);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
