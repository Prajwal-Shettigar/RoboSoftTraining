package com.prajwal.dependencyInjectionDemo;

public class TrackCoach extends Coach{


    public TrackCoach(FortuneService fortuneService) {
        super(fortuneService);
    }

    @Override
    public void getDailyWorkout() {
        System.out.println("Run hard 5k");
    }
}
