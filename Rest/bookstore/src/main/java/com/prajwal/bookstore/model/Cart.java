package com.prajwal.bookstore.model;


/*
{
"userId":1,
"bookId":1
}
 */
public class Cart {
    private int userId;
    private int bookId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
