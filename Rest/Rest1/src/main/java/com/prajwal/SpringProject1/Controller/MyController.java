package com.prajwal.SpringProject1.Controller;


import com.prajwal.SpringProject1.Model.Book;
import com.prajwal.SpringProject1.Service.BookOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyController {


    @Autowired
    BookOperations bookOperations;

    //get all books in list
    @GetMapping("/Books")
    public List<Book> getAllBooks(){
        return bookOperations.findAll();
    }


    //get book based on id
    @GetMapping("/Books/id/{id}")
    public Book getBookById(@PathVariable int id){
        return bookOperations.getBookById(id);
    }

    //get book based on name
    @GetMapping("/Books/name/{name}")
    public Book getBookByName(@PathVariable String name){
        return bookOperations.getBookByName(name);
    }

    //add a book onto the list
    @PostMapping("/Books")
    public void addBook(@RequestBody Book book){
        bookOperations.addABook(book);
    }

    //delete a book from the list
    @DeleteMapping("/Books/{id}")
    public void deleteBook(@PathVariable int id){
        bookOperations.removeBook(id);
    }
}
