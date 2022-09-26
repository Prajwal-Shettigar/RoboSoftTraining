package com.prajwal.manytomany.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.List;
/*
{
"name":"prajwal",
"computers":[{
"model":"dell"
},{
"model":"samsung"
}]
}

 */

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {


    @Id
    @GeneratedValue
    private int userId;
    private String name;

    @ManyToMany(targetEntity = Computer.class,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id",referencedColumnName = "userId")
    @JsonManagedReference
    @JsonIgnore
    private List<Computer> computers;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Computer> getComputers() {
        return computers;
    }

    public void setComputers(List<Computer> computers) {
        this.computers = computers;
    }
}
