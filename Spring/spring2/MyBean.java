package com.prajwal.spring2;


import org.springframework.stereotype.Service;

@Service
public class MyBean {

    public void someService(){
        System.out.println("service provided by mybean");
    }
}
