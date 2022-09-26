package com.prajwal.bookstore.service;

import com.prajwal.bookstore.model.Admin;
import com.prajwal.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //authenticate an admin
    public boolean authenticateAdmin(Admin admin){
        Admin admininDB =  jdbcTemplate.queryForObject("select * from admins where adminId="+admin.getAdminId(),Admin.class);

        if((admin.getName().equalsIgnoreCase(admininDB.getName())) && (admin.getPassword().equalsIgnoreCase(admininDB.getPassword()))){
            return true;
        }

        return false;
    }

    //insert a book into the books table
    public int insertABook(Book book){
        String insertQuery = "insert into books(name,price,available) values(?,?,?)";

        return jdbcTemplate.update(insertQuery,(preparedStatement)->{
            preparedStatement.setString(1,book.getBookName());
            preparedStatement.setInt(2,book.getPrice());
            preparedStatement.setBoolean(3,book.isAvailable());
        });
    }

    //remove a book based on id from books table
    public int removeABookById(int bookId){
        String deleteQuery = "delete from books where bookId="+bookId;

        return jdbcTemplate.update(deleteQuery);
    }


    //update the price of a book using the id
    public int updatePriceById(int bookId,int price){
        String updateQuery = "update books set price="+price+"where bookId="+bookId;
        return jdbcTemplate.update(updateQuery);
    }

    //select all available books
    public List<Book> getAllAvailableBooks(){
        String selectQuery = "select * from books where available=1";
        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(Book.class));

    }


    //get all books in book store
    public List<Book> getAllbooks(){
        String selectQuery = "select * from books";
        return jdbcTemplate.query(selectQuery,new BeanPropertyRowMapper<>(Book.class));
    }


    //get book by id
    public Book getBookById(int bookId){
        String selectQuery = "select * from books where bookId="+bookId;

        return jdbcTemplate.queryForObject(selectQuery,Book.class);
    }
}
