package com.prajwal.atprofile.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Profile("dev")
public class MyService {

    @Value("${message.val}")
    String message;

    @PostConstruct
    public void sayBye(){
        System.out.println("byeeeeeeeeeeeeeeeeee............");
    }

    public String printMessage(){
        return message;
    }

}
