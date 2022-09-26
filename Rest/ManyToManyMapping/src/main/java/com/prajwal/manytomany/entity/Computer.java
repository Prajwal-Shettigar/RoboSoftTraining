package com.prajwal.manytomany.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Computer {


    @Id
    @GeneratedValue
    private int compId;
    private String model;


    @ManyToMany(targetEntity = User.class,cascade = CascadeType.ALL,mappedBy = "computers")
    @JsonBackReference
//    @JoinColumn(name="comp_id",referencedColumnName = "compId")
    private List<User> users;



    public int getCompId() {
        return compId;
    }

    public void setCompId(int compId) {
        this.compId = compId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
