package com.prajwal.bookstore.controller;

import com.prajwal.bookstore.model.Book;
import com.prajwal.bookstore.model.Cart;
import com.prajwal.bookstore.model.User;
import com.prajwal.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/User")
public class UserController {


    @Autowired
    UserService userService;

    int sessionCode;

    //register an user
    @PutMapping("/Register")
    public String registerAnUser(@RequestBody User user){
        if(userService.insertAnUser(user)>0){
            sessionCode = new Random().nextInt(3000,8000);
            return "successfully registered...your session code is : "+sessionCode;
        }

        return "registration unsuccessful";
    }


    //user login
    @GetMapping("/Login")
    public String userLogin(@RequestBody User user){
        if(userService.authenticateAnUser(user)){
            sessionCode = new Random().nextInt(3000,8000);
            return "successful login...your session code is : "+sessionCode;
        }

        return "login unsuccessful";
    }

    //add books into cart
    @PutMapping("/Books/{sCode}")
    public String addToCart(@PathVariable int sCode,@RequestBody Cart cart){
        if(sCode==sessionCode){
            if(userService.addBooksIntoCart(cart)>0){
                return "books added successfully into cart";
            }
        }
        return "failed to add books into cart";
    }


    //remove a particular book from a user cart
    @DeleteMapping("/Books/{sCode}")
    public String removeBookInCart(@PathVariable int sCode,@RequestBody Cart cart){
        if(sCode==sessionCode){
            if(userService.removeCartBooks(cart)>0){
                return "book removed from cart successfully..";
            }
        }

        return "unable to remove book from cart..";
    }

    //buy all the books added into cart
    @PostMapping("/Books/{sCode}/{userId}")
    public String buyAllBooksInCart(@PathVariable int sCode,@PathVariable int userId){
        if(sessionCode==sCode){
            if(userService.buyBooks(userId)>0){
                return "books bought successfully...";
            }
        }

        return "unable to buy the books in cart...";
    }

    //get available books
    @GetMapping("/{sCode}/Books/Available")
    public ResponseEntity<List<Book>> getAllAvailableBooks(@PathVariable int sCode){
        if(sCode==sessionCode){
            return ResponseEntity.of(Optional.of(userService.getAllAvailableBooks()));

        }
        return ResponseEntity.noContent().build();
    }

    //get a book by id
    @GetMapping("/{sCode}/Books/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable int sCode, @PathVariable int bookId){
        if(sCode==sessionCode){
            return ResponseEntity.of(Optional.of(userService.getBookById(bookId)));
        }

        return ResponseEntity.noContent().build();
    }

    //get all books
    @GetMapping("/{sCode}/Books")
    public ResponseEntity<List<Book>> getAllBooks(@PathVariable int sCode){
        if(sCode==sessionCode){
            return ResponseEntity.of(Optional.of(userService.getAllbooks()));
        }
        return ResponseEntity.noContent().build();
    }
}
