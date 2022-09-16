package com.prajwal.springdemo;

import org.springframework.stereotype.Component;

public class HappyFortuneService implements FortuneService{

    //class which tells the fortune
    //private String fortune;

//    public HappyFortuneService(String fortune) {
//        this.fortune = fortune;
//    }

    @Override
    public void getFortune() {
        System.out.println("you will have a bright day ahead...");
    }
}
