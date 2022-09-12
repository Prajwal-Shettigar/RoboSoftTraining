package com.prajwal.SpringProject1.Service;

import com.prajwal.SpringProject1.Model.Book;

import java.util.List;

public interface BookOperations {


    //returns all books in library
    public List<Book> findAll();


    //returns book by id
    public Book getBookById(int id);


    //returns a book by name
    public Book getBookByName(String bookName);

    //adds a book onto the library
    public void addABook(Book book);

    //removes a book from library
    public void removeBook(int id);

}
