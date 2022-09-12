package com.prajwal.SpringProject1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {

        //runing my main application on tomcat server using spring
        SpringApplication.run(MainApplication.class,args);
    }
}
