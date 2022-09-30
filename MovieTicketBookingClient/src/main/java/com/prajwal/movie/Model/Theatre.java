package com.prajwal.movie.Model;


import java.io.Serializable;

/*
{
"id":1,
"name":"theatre 1",
"location":"udupi",
"cost":"240.00"
}
 */
public class Theatre implements Serializable {

    private int id;
    private String name;
    private String location;

    private double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
