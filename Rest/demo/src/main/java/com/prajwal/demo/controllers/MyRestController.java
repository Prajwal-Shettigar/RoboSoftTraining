package com.prajwal.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {


    @RequestMapping("/")
    public String getHello(){
        return "hello..jjjjjjjjjjjjjjj";
    }
}
