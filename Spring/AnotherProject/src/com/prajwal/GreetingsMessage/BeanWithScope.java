package com.prajwal.GreetingsMessage;

public class BeanWithScope {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }


    public void getMessage(){
        System.out.println("Message is : "+message);
    }

    public void init(){
        System.out.println("this is the init method");
    }

    public void destroy(){
        System.out.println("this is the destroy method");
    }
}
