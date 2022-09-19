package com.prajwal.productjpa.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(name = "Employee.findByNameandCity",query ="select * from employee  where name=?1 and city=?2",resultClass = Employee.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String city;

}
