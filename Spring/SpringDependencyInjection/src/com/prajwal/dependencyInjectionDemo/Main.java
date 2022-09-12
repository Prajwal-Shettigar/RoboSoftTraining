package com.prajwal.dependencyInjectionDemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {


    public static void main(String[] args) {

        //creating the bean container context path
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        //creating an object of beanname class which will dynamically provide the bean name
        BeanName beanName = classPathXmlApplicationContext.getBean("beanName", BeanName.class);

        //creating the object of coach based on name given by bean name
        Coach coach = classPathXmlApplicationContext.getBean(beanName.getBeanName(), Coach.class);

        //calling methods of coach

        coach.getDailyWorkout();

        coach.getDailyFortune();


        classPathXmlApplicationContext.close();


    }
}
