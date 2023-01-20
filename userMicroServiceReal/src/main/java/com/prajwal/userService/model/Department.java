package com.prajwal.userService.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
{
"departmentId":"",
"departmentName":"",
"departmentAddress":"",
"departmentCode":""
}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Department {

    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
