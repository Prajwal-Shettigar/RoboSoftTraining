package com.prajwal.dependencyInjectionDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCoach implements Coach{

    FortuneService fortuneService;

    @Autowired
    public void setFortuneService(FortuneService happyFortune2){
        this.fortuneService = happyFortune2;
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
