package com.prajwal.bookstore.model;


/*
{
"bookName":"book1",
"price":200,
"available":"true"
}
 */
public class Book {

    private int bookId;
    private String name;
    private int price;
    private boolean available;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
