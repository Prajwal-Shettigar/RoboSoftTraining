package com.prajwal.tryingjavaxml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        MyServices myServices=classPathXmlApplicationContext.getBean(MyServices.class);
        myServices.getService();


        classPathXmlApplicationContext.close();
    }
}
