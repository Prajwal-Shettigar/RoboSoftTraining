package com.prajwal.SpringProject1.Model;



//book pojo class
public class Book {

    private int bookId;
    private String bookName;
    private double bookCost;
    private String bookAuthor;


    public Book(){

    }


    public Book(int bookId, String bookName, double bookCost, String bookAuthor) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookCost = bookCost;
        this.bookAuthor = bookAuthor;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public double getBookCost() {
        return bookCost;
    }

    public void setBookCost(double bookCost) {
        this.bookCost = bookCost;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
