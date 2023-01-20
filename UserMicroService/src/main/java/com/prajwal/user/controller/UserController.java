package com.prajwal.user.controller;

import com.prajwal.user.entity.User;
import com.prajwal.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Users")
public class UserController {


    @Autowired
    UserService userService;

    public List<User> getAllUsers(){

    }
}
