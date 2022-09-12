package com.prajwal.hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        HelloWorld helloWorld = classPathXmlApplicationContext.getBean("helloWorld", HelloWorld.class);

        helloWorld.getMessage();
    }
}
