package com.prajwal.dependencyInjectionDemo;

import org.springframework.stereotype.Component;

public class HappyFortuneService implements FortuneService{

    //class which tells the fortune
    private String fortune;

    public HappyFortuneService(String fortune) {
        this.fortune = fortune;
    }

    @Override
    public void getFortune() {
        System.out.println(fortune);
    }
}
