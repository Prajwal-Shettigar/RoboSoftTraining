package com.prajwal.fileupload;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        SpringApplication.run(Main.class,args);
        String qu = " halleluyeah ";

        System.out.println(solve(qu.length(),qu));
    }



    public static String solve(int n,String s){

        System.out.println(n);
        System.out.println(s.length());


        String returnString = "";

        for(int i=0;i<n;i++){
            System.out.println(i);
            if(!(checkIfOvel(s.charAt(i)))){
                returnString = returnString+String.valueOf(s.charAt(i));
            }
        }

        return returnString.trim();
    }


    public static boolean checkIfOvel(char c){
        String ovels = "aeiouAEIOU";
        for(int j=0;j<ovels.length();j++){
            if(ovels.charAt(j)==c)
                return true;
        }
        return false;
    }
}
