package com.prajwal.springdemo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
//@ComponentScan(basePackages = "com.prajwal.springdemo")
public class MyConfiguration {
    @Autowired
    @Qualifier("fortunerService")
    FortuneService fortuneService;


    @Autowired
    @Qualifier("cricketCoach")
    Coach coach;

    @Bean
    public FortuneService fortuneService(){
        HappyFortuneService happyFortuneService = new HappyFortuneService();
        System.out.println("fortune service has code"+happyFortuneService.hashCode());
        return happyFortuneService;
    }

    @Bean
    public FortuneService fortunerService(){
        return fortuneService();
    }

    @Bean
    public CricketCoach cricketCoach(){
        return new CricketCoach(fortuneService);
    }

    @Bean
    public BaseBallCoach baseBallCoach(){
        return new BaseBallCoach();
    }

    @Bean("trackCoach")
    public TrackCoach trackCoach(){
        return new TrackCoach();
    }
}
