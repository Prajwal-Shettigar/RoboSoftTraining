package com.prajwal.GreetingsMessage;

public class Variables {


    private int a,b;

    Variables(int a){
        this.a=a;
    }

    Variables(int a,int b){
        this.a=a;
        this.b=b;
    }

    Variables(){

    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A:"+getA()+" B:"+getB()+" Sum = "+String.valueOf(getA()+getB());
    }
}
