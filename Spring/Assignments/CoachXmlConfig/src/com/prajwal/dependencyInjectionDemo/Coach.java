package com.prajwal.dependencyInjectionDemo;

public abstract class  Coach {

    FortuneService fortuneService;

    Coach(){

    }

    Coach(FortuneService fortuneService){
        this.fortuneService = fortuneService;
    }

//    setter for fortune service to try setter injection
    public void setFortuneService(FortuneService fortuneService){
        this.fortuneService=fortuneService;
    }


    //need to be implemented in child classes
    public abstract void getDailyWorkout();

    public void getDailyFortune(){
        fortuneService.getFortune();
    }
}
