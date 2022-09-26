package com.prajwal.bookstore.controller;


import com.prajwal.bookstore.model.Admin;
import com.prajwal.bookstore.model.Book;
import com.prajwal.bookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class BookStoreController {

    private int statusCode;

    @Autowired
    AdminService adminService;

    @GetMapping("/Admin")
    public int authenticateAdmin(@RequestBody Admin admin){
        if(adminService.authenticateAdmin(admin)){
            statusCode = new Random().nextInt(2,3000);
            return statusCode;
        }

        return 0;
    }

    @GetMapping("/Admin/{sCode}/Books")
    public List<Book> getAllBooks(@PathVariable int sCode){
        if(sCode==statusCode){
            return adminService.getAllbooks();
        }
        return new ArrayList<>();
    }

    @GetMapping("/Admin/{sCode}/Books/Available")
    public List<Book> getAllAvailableBooks(@PathVariable int sCode){
        if(sCode==statusCode){
            return adminService.getAllAvailableBooks();
        }
        return new ArrayList<>();
    }

   @GetMapping("/Admin/{sCode}/Books/{bookId}")
   public Book getBookById(@PathVariable int sCode,@PathVariable int bookId){
        if(sCode==statusCode){
            return adminService.getBookById(bookId);
        }

        return new Book();
   }


   @PutMapping("/Admin/{sCode}/Books")
    public String insertABook(@PathVariable int sCode,@RequestBody Book book){
        if(sCode==statusCode){
            if(adminService.insertABook(book)>0){
                return "Successfully inserted..";
            }
        }
        return "insertion Unsuccessful...";
   }
}
