package com.prajwal.hibernate;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student {


    @Id
    private int id;

    @Embedded
    private Name name;
    private String address;

    @ManyToMany
    private List<Laptop> laptops;

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = laptops;
    }

    public Student() {
        laptops = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name=" + name +
                ", address='" + address + '\'' +
                ", laptops=" + laptops +
                '}';
    }
}
