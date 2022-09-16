package com.prajwal.librarySystem;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String bookTitle;
    private int noOfCopies;
    private List<Borrower> borrowersHistory;


    public Book() {
        borrowersHistory = new ArrayList<>();
    }




    //setters and getters
    public List<Borrower> getBorrowersHistory() {
        return borrowersHistory;
    }

    public void setBorrowersHistory(List<Borrower> borrowersHistory) {
        this.borrowersHistory = borrowersHistory;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(int noOfCopies) {
        this.noOfCopies = noOfCopies;
    }



    //to string method
    @Override
    public String toString() {
        return "{ bookTitle='" + bookTitle + '\'' +
                ", noOfCopies=" + noOfCopies +
                '}';
    }
}
