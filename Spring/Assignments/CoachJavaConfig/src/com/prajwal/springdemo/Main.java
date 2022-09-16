package com.prajwal.springdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(MyConfiguration.class);
       // annotationConfigApplicationContext.refresh();

        Coach coach1 = annotationConfigApplicationContext.getBean("trackCoach",Coach.class);
        coach1.getDailyWorkout();
        coach1.getDailyFortune();

        Coach coach2 = annotationConfigApplicationContext.getBean("cricketCoach",Coach.class);
        coach2.getDailyWorkout();
        coach2.getDailyFortune();

        Coach coach3 = annotationConfigApplicationContext.getBean("baseBallCoach",Coach.class);
        coach3.getDailyWorkout();
        coach3.getDailyFortune();
    }
}
