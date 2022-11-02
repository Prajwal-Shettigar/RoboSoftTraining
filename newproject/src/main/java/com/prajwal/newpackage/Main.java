package com.prajwal.newpackage;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) throws JsonProcessingException {
//        SpringApplication.run(Main.class,args);

        String json = "{\n" +
                "  \"name\" : \"Mahesh\",\n" +
                "  \"age\" : 21\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        Student student = objectMapper.readValue(json, Student.class);

        System.out.println(student);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("server started running....");
    }
}

