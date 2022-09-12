package com.prajwal.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


//perfomrs variable auto wire
public class BaseBallCoach implements Coach{

    @Autowired
    FortuneService fortuneService;

    @Override
    public void getDailyWorkout() {
        System.out.println("Practice batting for 2 hours");
    }

    @Override
    public void getDailyFortune() {
        fortuneService.getFortune();
    }
}
