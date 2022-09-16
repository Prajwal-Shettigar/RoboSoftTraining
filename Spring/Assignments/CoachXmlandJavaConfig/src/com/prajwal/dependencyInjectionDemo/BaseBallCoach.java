package com.prajwal.dependencyInjectionDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BaseBallCoach implements Coach{

    @Autowired
    @Qualifier("happyFortune2")
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
