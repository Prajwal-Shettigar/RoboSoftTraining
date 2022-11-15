package com.robosoft.lorem.controller;

import com.robosoft.lorem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;


    //assign role to a user
    @PatchMapping("/Role/{userId}/{role}")
    public ResponseEntity<?> assignRole(@PathVariable int userId,@PathVariable String role){
        if(adminService.changeRole(userId, role))
            return new ResponseEntity<>("Role Changed Successfully..",HttpStatus.OK);

        return new ResponseEntity<>("Some Error Occurred While Changing Role..",HttpStatus.EXPECTATION_FAILED);
    }
}
