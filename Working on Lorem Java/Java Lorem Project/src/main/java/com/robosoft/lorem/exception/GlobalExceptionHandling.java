package com.robosoft.lorem.exception;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling
{
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<String> handleJWTToken(com.robosoft.lorem.exception.ExpiredJwtException e)
    {
        return new ResponseEntity<String>("Session has been Expired, Please login again", HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleErrors(Exception e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("Some error occurred...", HttpStatus.EXPECTATION_FAILED);

    }


    @ExceptionHandler(value= EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("NO content found...", HttpStatus.NO_CONTENT);

    }
    @ExceptionHandler(value= CartDeletedException.class)
    public ResponseEntity<String> handleCartDeletedExceptionException(CartDeletedException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("Cart deleted successfully......", HttpStatus.OK);

    }

    @ExceptionHandler(value= FirstNameNullException.class)
    public ResponseEntity<String> handleFirstNameNullException(FirstNameNullException e)
    {
        e.printStackTrace();
        return new ResponseEntity<String>("First name cannot be empty ", HttpStatus.OK);

    }


}


