package com.prajwal.userService.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/*
{
"id":"",
"firstName":"",
"lastName":"",
"email":"",
"departmentId":""
}
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long departmentId;

}
