package com.prajwal.dependencyInjectionDemo;

import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach{

    FortuneService fortuneService;

    public CricketCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public void getDailyWorkout() {
        System.out.println("Practice fast bowling for 15 minutes..");
    }


    @Override
    public void getDailyFortune() {
        fortuneService.getFortune();
    }
}
