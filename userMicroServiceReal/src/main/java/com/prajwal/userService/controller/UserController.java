package com.prajwal.userService.controller;


import com.prajwal.userService.entity.User;
import com.prajwal.userService.model.UserDepoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.prajwal.userService.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;


    //get all users
    @GetMapping("/All")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }




    //get user by id
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") long id){
        return userService.getUserById(id);
    }




    //save a user
    @PostMapping("/User")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    //get the user along with department
    @GetMapping("/User-Department/{id}")
    public UserDepoResponse getUserWithDepartment(@PathVariable("id") long id){
        return userService.getUserAlongWithDepartment(id);
    }


}
