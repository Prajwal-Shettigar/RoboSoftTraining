package com.prajwal.messagingservice.exceptions;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.mail.MessagingException;

@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<?> onMessagingException(MessagingException messagingException){
        messagingException.printStackTrace();
        return new ResponseEntity<>("Failed to Send A Email..",HttpStatus.EXPECTATION_FAILED);
    }
}
