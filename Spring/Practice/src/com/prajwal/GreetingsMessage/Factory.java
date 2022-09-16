package com.prajwal.GreetingsMessage;

public class Factory {

    public static SomeClass getSomeClass(){
        return new SomeClass();
    }


    public SomeClass giveSomeClass(){
        return new SomeClass();
    }
}
