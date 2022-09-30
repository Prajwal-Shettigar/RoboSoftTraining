package com.prajwal.atprofile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Configuration
@Profile("dev")
public class MyConfig {

    @PostConstruct
    public void sayHello(){
        System.out.println("Hellooooooo...............");
    }
}
