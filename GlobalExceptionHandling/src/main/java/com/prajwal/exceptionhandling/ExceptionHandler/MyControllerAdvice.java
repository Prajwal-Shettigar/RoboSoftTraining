package com.prajwal.exceptionhandling.ExceptionHandler;


import com.prajwal.exceptionhandling.exception.ExceptionOne;
import com.prajwal.exceptionhandling.exception.ExceptionThree;
import com.prajwal.exceptionhandling.exception.ExceptionTwo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice {


//    @ExceptionHandler(value = ExceptionOne.class)
//    public ResponseEntity<Object> forExceptionOne(ExceptionOne exceptionOne){
//        return new ResponseEntity<>("exception one was thrown..", HttpStatus.INTERNAL_SERVER_ERROR);
//    }


    @ExceptionHandler(value = ExceptionTwo.class)
    public ResponseEntity<Object> forExceptionTwo(ExceptionTwo exceptionTwo){
        return new ResponseEntity<>("exception two was thrown..", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = ExceptionThree.class)
    public ResponseEntity<Object> forExceptionThree(ExceptionThree exceptionThree){
        return new ResponseEntity<>("exception three was thrown..", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value=Exception.class)
    public ResponseEntity<Object> forAllExceptions(Exception e){
        return new ResponseEntity<>("for all exceptions..",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
