package com.prajwal.GreetingsMessage;

public class CalculateSum {

    private int a,b;
    Variables variables;


    CalculateSum(int a,int b){
        this.a=a;
        this.b=b;
    }

    CalculateSum(Variables variables){
        System.out.println("only one arg");
        this.variables=variables;
    }

    CalculateSum(Variables variables,String message){
        System.out.println(message);
       this.variables=variables;
    }

    CalculateSum(Variables variables,PrintListOfValues printListOfValues){
        System.out.println("two arg arg");
        this.variables=variables;
    }

    CalculateSum(){

    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    public void getSum(){
        System.out.println("sum is = "+(variables.getA()+variables.getB()));
    }

    public void sum(){
        System.out.println("sum is "+(a+b));
    }
}
