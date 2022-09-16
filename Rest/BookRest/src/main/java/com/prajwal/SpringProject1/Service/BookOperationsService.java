package com.prajwal.SpringProject1.Service;

import com.prajwal.SpringProject1.Model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookOperationsService implements BookOperations{

    List<Book> books;


    //added books onto the book list during initialization
    public BookOperationsService(){

        books = new ArrayList<>();

        books.add(new Book(1,"book1",20,"author1"));
        books.add(new Book(2,"book2",30,"author2"));
        books.add(new Book(3,"book3",20,"author1"));
        books.add(new Book(4,"book4",20,"author4"));
        books.add(new Book(5,"book5",50,"author3"));
    }


    //to get all books in library
    @Override
    public List<Book> findAll() {
        return books;
    }


    //find a book based on its id
    @Override
    public Book getBookById(int id) {
        return books.stream().filter(book->book.getBookId()==id).findAny().orElse(null);
    }


    //find a book based on its name
    @Override
    public Book getBookByName(String bookName) {
        return books.stream().filter(book->book.getBookName().equalsIgnoreCase(bookName)).findAny().orElse(null);
    }


    //add a book onto the list
    @Override
    public void addABook(Book book) {
        books.add(book);
    }


    //remove a book based on its id
    @Override
    public void removeBook(int id) {
        Book book = getBookById(id);
        books.remove(book);
    }
}
