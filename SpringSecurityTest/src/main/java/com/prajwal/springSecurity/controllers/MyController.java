package com.prajwal.springSecurity.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {


    @GetMapping("/")
    public String forEveryOne(){
        return "welcome everyone..";
    }



    @GetMapping("/Admin")
    public String forAdmin(){
        return "welcome admin..";
    }


    @GetMapping("/User")
    public String forUser() {
        return "welcome admin or user...";
        }
}
