package com.prajwal.librarySystem;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class Clerk extends User{

    String password;


//    default constructor
    public Clerk() {

        otherScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);

    }


//    setters and getters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //view book history of borrowes
    public void viewBorrowHistory(){
        System.out.println("Enter the title of the book : ");
        String bookTitle = stringScanner.nextLine();
        library.viewLoanHistory(bookTitle);
    }


    //to add a new borrower onto the library
    public void addABorrower(ClassPathXmlApplicationContext classPathXmlApplicationContext){

        Borrower borrower = classPathXmlApplicationContext.getBean("newBorrowe", Borrower.class);
        System.out.println("Enter the borrower id : ");
        borrower.setId(otherScanner.nextInt());
        System.out.println("Enter the borrower name : ");
        borrower.setName(stringScanner.nextLine());
        System.out.println("Enter the borrower address : ");
        borrower.setAddress(stringScanner.nextLine());
        System.out.println("Enter the borrower phone number : ");
        borrower.setPhoneNumber(otherScanner.nextLong());

        borrower.setLibrary(library);

        library.addBorrower(borrower);
    }

}
