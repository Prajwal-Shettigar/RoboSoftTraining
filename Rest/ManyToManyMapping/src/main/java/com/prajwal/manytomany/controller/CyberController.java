package com.prajwal.manytomany.controller;


import com.prajwal.manytomany.entity.User;
import com.prajwal.manytomany.service.CyberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CyberController {


    @Autowired
    CyberService cyberService;


    @PutMapping("/User")
    public User addUser(@RequestBody User user){
        return cyberService.saveUser(user);
    }

    @GetMapping("/Users")
    public List<User> getAllUsers(){
        return cyberService.getAllUsers();
    }
}
