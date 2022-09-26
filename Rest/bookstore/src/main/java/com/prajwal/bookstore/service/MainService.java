package com.prajwal.bookstore.service;


import com.prajwal.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MainService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //select all available books
    public List<Book> getAllAvailableBooks(){
        String selectQuery = "select * from books where available=1";
        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(Book.class));

    }


    //get book by id
    public Book getBookById(int bookId){
        String selectQuery = "select * from books where bookId="+bookId;

        return jdbcTemplate.query(selectQuery,(resultSet)->{
            Book book = new Book();

            resultSet.next();
            book.setBookId(resultSet.getInt(1));
            book.setName(resultSet.getString(2));
            book.setPrice(resultSet.getInt(3));
            book.setAvailable(resultSet.getBoolean(4));
            return book;
        });
    }

    //get all books in book store
    public List<Book> getAllbooks(){
        String selectQuery = "select * from books";
        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(Book.class));
    }

}
