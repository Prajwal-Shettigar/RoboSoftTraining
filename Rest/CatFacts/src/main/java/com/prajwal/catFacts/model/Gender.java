package com.prajwal.catFacts.model;
/*
{"count":86426,"gender":"male","name":"luc","probability":0.99}
 */
public class Gender {
    private long count;
    private String gender;
    private String name;
    private double probability;


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
