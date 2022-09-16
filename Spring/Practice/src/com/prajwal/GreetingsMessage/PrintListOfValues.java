package com.prajwal.GreetingsMessage;

import java.util.List;
import java.util.Map;

public class PrintListOfValues {

    List<Variables> myList;

    Map<Integer,Variables> map;

//    PrintListOfValues(List<Variables> list){
//        this.list=list;
//    }
//
//
//
//    PrintListOfValues(Map<Integer,Variables> map){
//        this.map=map;
//    }



    public void printMap(){
        map.forEach((key,value)->{
            System.out.println("key : "+key +" value : "+value);
        });
    }

    public void setMyList(List<Variables> myList) {
        this.myList = myList;
    }

    public void printList(){
        System.out.println("list values are : ");
        myList.forEach(System.out::println);
    }
}
