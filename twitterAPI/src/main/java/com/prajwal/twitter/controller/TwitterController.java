package com.prajwal.twitter.controller;


import com.prajwal.twitter.entity.Users;
import com.prajwal.twitter.model.RegistrationModel;
import com.prajwal.twitter.service.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Random;

@RestController
@RequestMapping("/Twitter")
public class TwitterController {

    @Autowired
    TwitterService twitterService;

    private int sessionId;
    private String userId;

    //register
    @PostMapping("/Register")
    public ResponseEntity<Integer> registerAUser(@ModelAttribute RegistrationModel model) throws IOException {


        //if user by that username doesnt exists then
        if(getUserById(model.getUserId()).getStatusCode().value()==HttpStatus.NO_CONTENT.value()){


            //create a user
            Users users = new Users(model.getName(),model.getUserId(), model.getPassword(), false,model.getEmail(),model.getPhoneNumber());


            //check if profile photo is sent
            if(!model.getFile().isEmpty()){
                users.setProfilePhoto(model.getFile().getBytes());
            }

            //register the user
            Users returnedUser = twitterService.registerUser(users);

            if(returnedUser!=null){

                //store username and session id
                this.userId = returnedUser.getUserId();

                this.sessionId = new Random().nextInt(1000,20000);


                return ResponseEntity.accepted().body(sessionId);
            }
        }

        //if user by that username is present then
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }



    //login

    //list of top accounts to follow

    //home

    //post a tweet

    //get comment on a tweet

    //follow

    //unfollow

    //like a post

    //post a comment on a tweet

    //get followers

    //find users by username

    //find tweets by tag

    //find user by user id
    @GetMapping("/User/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable String id){
        //get the user from service
        Users  users = twitterService.getUserById(id);

        if(users!=null)
            return ResponseEntity.ok(users);

        return ResponseEntity.noContent().build();
    }



    //check session id
    private boolean checkSessionId(int sessionId){
        return sessionId == this.sessionId;
    }


}
