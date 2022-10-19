package com.prajwal.securityjpa.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/")
    public String everyone(){
        return "hello everyone...";
    }


    @GetMapping("/Admin")
    public String forAdmin(){
        return "hello admin..";
    }


    @GetMapping("/User")
    public String forUser(){
        return "hello user..";
    }


}
