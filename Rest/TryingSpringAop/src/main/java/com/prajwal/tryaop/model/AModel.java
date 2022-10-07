package com.prajwal.tryaop.model;


import org.springframework.stereotype.Component;

@Component
public class AModel {

    private int a=10;

    private int b=20;

    private String c = "hello";

    private double d = 9.0909;

    private boolean e=true;


    @MyAnnotation
    public int getA() {
        return a;
    }


    @MyAnnotation
    public int getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public boolean isE() {

        throw new RuntimeException("my exception");
    }

    public boolean isG(){
        return true;
    }

    public String getString(String arg){
        return arg;
    }
}
