package com.prajwal.GreetingsMessage;

import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
//
//        String greetingType = new Scanner(System.in).nextLine();
//
//        Greetings greetings = applicationContext.getBean(greetingType,Greetings.class);
//
//        greetings.getGreetings();


//        CalculateSum calculateSum = applicationContext.getBean("calculateSum", CalculateSum.class);
//        calculateSum.getSum();


//        PrintListOfValues printListOfValues = applicationContext.getBean("printlistofvalues", PrintListOfValues.class);
//        printListOfValues.printMap();

//        CalculateSum calculateSum = applicationContext.getBean("calculateSum", CalculateSum.class);
//        calculateSum.getSum();

//        PrintListOfValues printListOfValues = applicationContext.getBean("printListOfValues", PrintListOfValues.class);
//        printListOfValues.printList();

//        SomeClass someClass = applicationContext.getBean("someClass", SomeClass.class);
//        someClass.method();


//        BeanWithScope beanWithScope = applicationContext.getBean("beanWithScope",BeanWithScope.class);
//        beanWithScope.setMessage("prajwal");
//        beanWithScope.getMessage();
//
//        BeanWithScope beanWithScopee = applicationContext.getBean("beanWithScope2",BeanWithScope.class);
//        beanWithScopee.getMessage();

//        beanWithScopee=null;
//        beanWithScope =null;
//        System.gc();
//
//        try{
//            Thread.sleep(2000);
//        }catch(InterruptedException interruptedException){
//            System.out.println("interrupted...");
//        }



        ForAutoWiring forAutoWiring = applicationContext.getBean("forAutoWiring", ForAutoWiring.class);
        forAutoWiring.someMethod();
        applicationContext.close();

    }
}
