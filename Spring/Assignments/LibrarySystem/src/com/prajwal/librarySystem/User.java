package com.prajwal.librarySystem;


import java.util.Scanner;

public abstract class User {

    public Scanner otherScanner , stringScanner;
    int id;
    String name;
    String address;
    long phoneNumber;

    Library library;


    //setters and getters
    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //search for a book in library
    public void searchABook(){
        System.out.println("Enter the book title : ");
        String bookTitle = stringScanner.nextLine();
        Book book = library.serachABookByTitle(bookTitle);

        if(book == null){
            System.out.println("The book by the titile : "+bookTitle +" is not present in our library..");
            return;
        }

        System.out.println(book);

    }

    public abstract void viewBorrowHistory();
}
