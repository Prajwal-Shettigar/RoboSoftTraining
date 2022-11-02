package com.prajwal.twitter.exception;

import com.prajwal.twitter.customeExceptions.DeletionFailed;
import com.prajwal.twitter.customeExceptions.DuplicateUsername;
import com.prajwal.twitter.customeExceptions.NoContentToDisplay;
import com.prajwal.twitter.customeExceptions.UserDoesNotExists;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<?> malformedJwtException(MalformedJwtException malformedJwtException){
        return new ResponseEntity<>("Malformed Jwt!!", HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = DuplicateUsername.class)
    public ResponseEntity<?> duplicateUsernameException(DuplicateUsername duplicateUsername){
        return new ResponseEntity<>("username already exists..",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoContentToDisplay.class)
    public ResponseEntity<?> duplicateUsernameException(NoContentToDisplay noContentToDisplay){
        return new ResponseEntity<>("no content to display..",HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = UserDoesNotExists.class)
    public ResponseEntity<?> duplicateUsernameException(UserDoesNotExists userDoesNotExists){
        return new ResponseEntity<>("user does not exists..",HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = DeletionFailed.class)
    public ResponseEntity<?> duplicateUsernameException(DeletionFailed deletionFailed){
        return new ResponseEntity<>("deletion failed..",HttpStatus.EXPECTATION_FAILED);
    }


    //using inbuilt handlers
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>("change the method of the request..",HttpStatus.METHOD_NOT_ALLOWED);
    }



}
