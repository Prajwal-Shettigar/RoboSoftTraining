package com.prajwal.librarySystem;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.Scanner;

public class LibrayApp {


    public static Scanner otherScanner , stringScanner;
    public static ClassPathXmlApplicationContext classPathXmlApplicationContext;

    public static Library library;


    public static void main(String[] args) {

        //scanners to take input from user
        otherScanner = new Scanner(System.in);
        stringScanner = new Scanner(System.in);

         classPathXmlApplicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");


        //create a library with some intial books
         library = classPathXmlApplicationContext.getBean(Library.class);
        userPrompt();


    }





    //main user prompt
    public static void userPrompt(){
        while(true){
            System.out.println("Select type of user : \n" +
                    "1.Librarian \n" +
                    "2.Clerk \n" +
                    "3.Borrower \n" +
                    "4. Exit" );

            switch(otherScanner.nextInt()){
                case 1:
                    librarianPrompt();
                    break;
                case 2:
                    clerkPrompt();
                    break;
                case 3:
                    borrowerPrompt();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter a valid choice..");
            }
        }
    }

    //user prompt for librarian
    public static void librarianPrompt(){
        System.out.println("Enter the username : ");
        String username = stringScanner.nextLine();

        System.out.println("Enter the password : ");
        String password = stringScanner.nextLine();

        Librarian librarian = classPathXmlApplicationContext.getBean(Librarian.class);

        if(username.equalsIgnoreCase(librarian.getName()) && password.equalsIgnoreCase(librarian.getPassword())) {

            librarian.setLibrary(library);


            while (true) {
                System.out.println("Select type of operation : \n" +
                        "1.Search a book by title \n" +
                        "2.View borrow history \n" +
                        "3.Add a Borrower \n" +
                        "4. Add a new Book \n" +
                        "5. Delete a Book \n" +
                        "6. Back ");

                switch (otherScanner.nextInt()) {
                    case 1:
                        librarian.searchABook();
                        break;
                    case 2:
                        librarian.viewBorrowHistory();
                        break;
                    case 3:
                        librarian.addABorrower(classPathXmlApplicationContext);
                        break;
                    case 4:
                        librarian.addANewBook(classPathXmlApplicationContext);
                        break;
                    case 5:
                        librarian.deleteABook();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Enter a valid choice..");

                }
            }
        }

        System.out.println("Invalid credentials...");


    }


//    user Prompt for clerk
    public static void clerkPrompt(){

        System.out.println("Enter the username : ");
        String username = stringScanner.nextLine();

        System.out.println("Enter the password : ");
        String password = stringScanner.nextLine();

        Clerk clerk = classPathXmlApplicationContext.getBean(Clerk.class);

        if(username.equalsIgnoreCase(clerk.getName()) && password.equalsIgnoreCase(clerk.getPassword())) {


            clerk.setLibrary(library);
            while (true) {
                System.out.println("Select type of operation : \n" +
                        "1.Search a book by title \n" +
                        "2.View borrow history \n" +
                        "3.Add a Borrower \n" +
                        "4. Back \n");


                switch (otherScanner.nextInt()) {
                    case 1:
                        clerk.searchABook();
                        break;
                    case 2:
                        clerk.viewBorrowHistory();
                        break;
                    case 3:
                        clerk.addABorrower(classPathXmlApplicationContext);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Enter a valid choice..");
                }
            }
        }
        System.out.println("invalid credentials..");

    }



//    user Prompt for the borrower
    public static void borrowerPrompt(){
        System.out.println("Enter the borrower ID : ");
        int id = otherScanner.nextInt();

        Borrower borrower = null;

        for(Borrower bor : library.getBorrowersInLibrary()){
            if(bor.getId() == id){
                borrower = bor;
            }
        }

        if(borrower == null){
            System.out.println("Invalid borrower ID");
            return;
        }


        borrower.setLibrary(library);

        while(true) {
            System.out.println("Select type of operation : \n" +
                    "1.Search a book by title \n" +
                    "2.View borrow history \n" +
                    "3.Request a Loan \n" +
                    "4.Back ");

            switch (otherScanner.nextInt()){
                case 1:
                    borrower.searchABook();
                    break;
                case 2:
                    borrower.viewBorrowHistory();
                    break;
                case 3:
                    borrower.loanABook();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter a valid choice..");

            }
        }
    }

}

