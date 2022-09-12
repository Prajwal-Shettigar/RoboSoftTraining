package com.prajwal.GreetingsMessage;

import org.springframework.beans.factory.annotation.Autowired;

public class ForAutoWiring {


    @Autowired
    private SomeClass someClass;

//    @Autowired
//    public void setSomeClass(SomeClass someClass){
//        this.someClass=someClass;
//    }


//    ForAutoWiring(SomeClass someClass){
//        this.someClass = someClass;
//    }

    public void someMethod(){
        someClass.method();
    }
}
