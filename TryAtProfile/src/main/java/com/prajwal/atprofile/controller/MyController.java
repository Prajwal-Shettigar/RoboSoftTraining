package com.prajwal.atprofile.controller;


import com.prajwal.atprofile.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/My")
public class MyController {


    @Autowired
    MyService myService;

    @GetMapping
    public String printMessage(){
        return myService.printMessage();
    }
}
