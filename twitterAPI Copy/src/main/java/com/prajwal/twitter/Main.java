package com.prajwal.twitter;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;

//@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        SpringApplication.run(Main.class,args);

        System.out.println(randomTokenGenerator(30));
    }



    //random token generator
    public static String randomTokenGenerator(int length){
        //48-122
        StringBuilder token = new StringBuilder();

        String mychars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";


        Random random = new Random();

        for(int i=0;i<length;i++){
            token.append(mychars.charAt(random.nextInt(0, mychars.length())));
        }

        return token.toString();
    }
}
