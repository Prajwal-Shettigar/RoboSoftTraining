package com.prajwal.dependencyInjectionDemo;

public class CricketCoach extends Coach{

    @Override
    public void getDailyWorkout() {
        System.out.println("Practice fast bowling for 15 minutes..");
    }


    @Override
    public void getDailyFortune() {
        super.getDailyFortune();
    }
}
