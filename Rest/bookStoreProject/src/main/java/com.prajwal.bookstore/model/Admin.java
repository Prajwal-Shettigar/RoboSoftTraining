package com.prajwal.bookstore.model;

/*
{
"adminId":1,
"name":"prajwal",
"password":"password"
}
 */
public class Admin {
    private int adminId;
    private String name;
    private String password;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
