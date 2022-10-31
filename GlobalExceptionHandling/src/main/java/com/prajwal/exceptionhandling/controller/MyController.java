package com.prajwal.exceptionhandling.controller;


import com.prajwal.exceptionhandling.exception.ExceptionOne;
import com.prajwal.exceptionhandling.exception.ExceptionThree;
import com.prajwal.exceptionhandling.exception.ExceptionTwo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/Exception")
public class MyController {
    static ArrayList<String> messages;

    static {
        messages = new ArrayList<>();
        messages.add("message 1");
        messages.add("message 2");
        messages.add("message 3");

    }


    @GetMapping("/One/{choice}")
    public ResponseEntity<Object> exceptionOne(@PathVariable int choice) throws ExceptionOne {
        if(this.throwOrNot(choice))
            throw new ExceptionOne();

        return ResponseEntity.ok(messages);
    }
    @GetMapping("/Two/{choice}")
    public ResponseEntity<ArrayList<String>> exceptionTwo(@PathVariable int choice) throws ExceptionTwo {
        if(this.throwOrNot(choice))
            throw new ExceptionTwo();

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/Three/{choice}")
    public ResponseEntity<ArrayList<String>> exceptionThree(@PathVariable int choice) throws ExceptionThree {
        if(this.throwOrNot(choice))
            throw new ExceptionThree();

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/Global/{choice}")
    public ResponseEntity<ArrayList<String>> exceptionGlobal(@PathVariable int choice) throws Exception {
        if(this.throwOrNot(choice))
            throw new Exception();

        return ResponseEntity.ok(messages);
    }


    public boolean throwOrNot(int choice){
        return choice!=0;
    }
}
