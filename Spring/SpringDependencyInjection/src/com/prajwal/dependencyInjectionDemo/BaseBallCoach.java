package com.prajwal.dependencyInjectionDemo;

public class BaseBallCoach extends Coach{

    public BaseBallCoach(FortuneService fortuneService) {
        super(fortuneService);
    }

    @Override
    public void getDailyWorkout() {
        System.out.println("Practice batting for 2 hours");
    }
}
