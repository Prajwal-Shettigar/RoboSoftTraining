package com.prajwal.hibernate;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Laptop {

    @Id
    private int lapId;
    private String brand;


    @ManyToMany(mappedBy = "laptops")
    private List<Student> students;

    public Laptop() {

        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public int getLapId() {
        return lapId;
    }

    public void setLapId(int lapId) {
        this.lapId = lapId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    @Override
    public String toString() {
        return "Laptop{" +
                "lapId=" + lapId +
                ", brand='" + brand + '\'' +
                ", students=" + students +
                '}';
    }
}
