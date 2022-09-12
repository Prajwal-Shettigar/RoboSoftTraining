package com.prajwal.librarySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Borrower extends User{



    private List<Book> borrowedBooks;


    // default constructor
    public Borrower() {
        this.borrowedBooks = new ArrayList<>();
        otherScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);

    }


    //setters and getters
    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //view borrow history of the borrower
    public void viewBorrowHistory(){
        for(Book  book:borrowedBooks){
            System.out.println(book.getBookTitle());
        }
    }


    //loan a book from library
    public void loanABook(){
        System.out.println("Enter the title of the book : ");
        Book book = library.serachABookByTitle(stringScanner.nextLine());

        if(book == null){
            System.out.println("Requested book is not in our library..");
            return;
        }

        library.borrowABook(this,book);
    }

}
