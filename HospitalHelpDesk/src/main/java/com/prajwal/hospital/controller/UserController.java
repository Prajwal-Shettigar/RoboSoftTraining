package com.prajwal.hospital.controller;

import com.prajwal.hospital.model.Insurance;
import com.prajwal.hospital.model.User;
import com.prajwal.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    //get insured
    @PostMapping("/Insurance/{amount}")
    public ResponseEntity<Insurance> getInsured(@RequestBody User user, @PathVariable Double amount){

        return ResponseEntity.ok(userService.insureUser(new Insurance(user.getName(),user.getAge(),user.getGender(),amount)));
    }
}
