package com.prajwal.librarySystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.BeanProperty;

@Configuration
public class Configurationclass {



    //creating beans of book to be inserted into the library initially
    @Bean
    public Book book1(){
        Book book = new Book();
        book.setBookTitle("A TIME TO KILL");
        book.setNoOfCopies(3);
        return book;
    }

    @Bean
    public Book book2(){
        Book book = new Book();
        book.setBookTitle("EAST OF EDEN");
        book.setNoOfCopies(3);
        return book;
    }

    @Bean
    public Book book3(){
        Book book = new Book();
        book.setBookTitle("THE SUN ALSO RISES");
        book.setNoOfCopies(3);
        return book;
    }

    @Bean
    public Librarian librarian(){
        Librarian librarian  = new Librarian();
        librarian.setId(101);;
        librarian.setName("prajwal");
        librarian.setAddress("udupi");
        librarian.setPhoneNumber(761376175);
        librarian.setPassword("9004");


        return librarian;
    }

    @Bean
    public Clerk clerk() {
        Clerk clerk = new Clerk();

        clerk.setId(201);
        clerk.setName("pj");
        clerk.setAddress("udupi");
        clerk.setPhoneNumber(9880587);
        clerk.setPassword("9004");

        return clerk;

    }

    @BeanPropertypublic Borrower borrower1(){

    }

}
