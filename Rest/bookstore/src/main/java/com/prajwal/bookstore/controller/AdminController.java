package com.prajwal.bookstore.controller;


import com.prajwal.bookstore.model.Admin;
import com.prajwal.bookstore.model.Book;
import com.prajwal.bookstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    private int statusCode;

    @Autowired
    AdminService adminService;


    //admin authentication
    @GetMapping()
    public String authenticateAdmin(@RequestBody Admin admin){
        System.out.println("inside admin..");
        if(adminService.authenticateAdmin(admin)){
            statusCode = new Random().nextInt(2,3000);
            return "Successfull login your status code is : "+statusCode;
        }

        return "login unsuccessful";
    }

    //get all books
    @GetMapping("/{sCode}/Books")
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int sCode){
        if(sCode==statusCode){
            return ResponseEntity.of(Optional.of(adminService.getAllbooks()));
        }
        return ResponseEntity.noContent().build();
    }

    //get available books
    @GetMapping("/{sCode}/Books/Available")
    public ResponseEntity<List<Book>> getAllAvailableBooks(@PathVariable int sCode){
        if(sCode==statusCode){
            return ResponseEntity.of(Optional.of(adminService.getAllAvailableBooks()));

        }
        return ResponseEntity.noContent().build();
    }


    //get a book by id
   @GetMapping("/{sCode}/Books/{bookId}")
   public ResponseEntity<Book> getBookById(@PathVariable int sCode, @PathVariable int bookId){
        if(sCode==statusCode){
            return ResponseEntity.of(Optional.of(adminService.getBookById(bookId)));
        }

        return ResponseEntity.noContent().build();
   }



   //insert a book into db
   @PutMapping("/{sCode}/Books")
    public String insertABook(@PathVariable int sCode,@RequestBody Book book){
        if(sCode==statusCode){
            if(adminService.insertABook(book)>0){
                return "Successfully inserted..";
            }
        }
        return "insertion Unsuccessful...";
   }


   //update the price of a book
   @PatchMapping("/{sCode}/Books/{bookId}/{price}")
    public String updateBookPrice(@PathVariable int sCode,@PathVariable int bookId,@PathVariable int price){
       if(sCode==statusCode){
           if(adminService.updatePriceById(bookId,price)>0){
               return "Successfully updated..";
           }
       }
       return "updation Unsuccessful...";
   }

   //delete a book using id
    @DeleteMapping("/{sCode}/Books/{bookId}")
    public String deleteABookById(@PathVariable int sCode,@PathVariable int bookId){
        if(sCode==statusCode){
            if(adminService.removeABookById(bookId)>0){
                return "Successfully deleted..";
            }
        }
        return "deletion Unsuccessful...";
    }
}
