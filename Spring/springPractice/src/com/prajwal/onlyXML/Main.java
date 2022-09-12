package com.prajwal.onlyXML;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {


        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("OnlyXMLConfiguration.xml");

        BasicClass basicClass = classPathXmlApplicationContext.getBean("basicClass", BasicClass.class);

        System.out.println(basicClass);

    }


}
