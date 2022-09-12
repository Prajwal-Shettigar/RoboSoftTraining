package com.prajwal.spring2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(MyContainer.class);


        annotationConfigApplicationContext.refresh();


        MyBean myBean = annotationConfigApplicationContext.getBean("myBean", MyBean.class);
        myBean.someService();
    }
}
