package com.prajwal.messagingservice.controllers;


import com.prajwal.messagingservice.service.EmailService;
import com.prajwal.messagingservice.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MessagingController {


    @Autowired
    MessagingService messagingService;

    @Autowired
    EmailService emailService;


    @GetMapping("/Message/{message}/{mobileNumber}")
    public HttpStatus sendMeAMessage(@PathVariable String message,@PathVariable String mobileNumber){

        messagingService.send(message,mobileNumber);

        return HttpStatus.OK;
    }


    @GetMapping("/Email/{body}/{address}")
    public HttpStatus sendMeAEmail(@PathVariable String body,@PathVariable String address) throws MessagingException {


        emailService.send(body,address);

        return HttpStatus.OK;

    }
}
