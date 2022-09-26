package com.prajwal.moviesystem.Model;


/*
{
"id":1,
"fname":"prajwal",
"lname":"shettigar",
"phoneNumber":7619376175,
"location":"udupi",
"email":"prajwalshettigar4216@gmail.com"
}
 */
public class User {

    private int id;
    private String fname;
    private String lname;
    private long phoneNumber;
    private String location;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
