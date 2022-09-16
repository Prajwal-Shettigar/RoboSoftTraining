package com.prajwal.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


//performs setter autowire
public class TrackCoach implements Coach{

    FortuneService fortuneService;

    @Autowired
    public void setFortuneService(FortuneService fortuneService){
        this.fortuneService = fortuneService;
    }

    @Override
    public void getDailyWorkout() {
        System.out.println("Run hard 5k");
    }


    @Override
    public void getDailyFortune() {
        fortuneService.getFortune();
    }
}
