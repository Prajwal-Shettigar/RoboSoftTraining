package com.prajwal.onlyXML;

public class PrintsString {
    String setterMessage;         //for setter innjection

    String constructorMessage;        //for constructor injection

    PrintsString(String constructorMessage){
        this.constructorMessage = constructorMessage;
    }


    public void setSetterMessage(String setterMessage) {
        this.setterMessage = setterMessage;
    }

    public String printMessage(){
       return "setterMessage : "+setterMessage+ "constructor message : "+constructorMessage;
    }
}
