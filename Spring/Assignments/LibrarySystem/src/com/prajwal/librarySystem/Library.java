package com.prajwal.librarySystem;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class Library {


    private  List<Book> booksInLibrary;

    @Autowired
    private Librarian librarian;

    @Autowired
    private  Clerk clerk;

    private List<Borrower> borrowersInLibrary;


//constructor
    public Library(List<Borrower> borrowersInLibrary,List<Book> bookList) {
        this.borrowersInLibrary = borrowersInLibrary;
        this.booksInLibrary = bookList;
        this.librarian = librarian;
        this.clerk = clerk;
    }

    //    for searching a book based on the title
    public  Book serachABookByTitle(String bookTitle){
        if(booksInLibrary == null){
            return null;
        }
        for(Book book:booksInLibrary){
            if(book.getBookTitle().equalsIgnoreCase(bookTitle)){
                return book;
            }
        }

        return null;
    }


    //view the borrowers history for a particular book using its name
    public   void viewLoanHistory(String bookTitle){
        Book book = serachABookByTitle(bookTitle);

        if(book==null){
            System.out.println("Book by the name : "+bookTitle+" not present in our library..");
            return;
        }

        System.out.println("Borrowers history of book : "+bookTitle+" :");

        for(Borrower borrower : book.getBorrowersHistory()){
            System.out.println(borrower.getName());
        }
    }


//add a book into the library if already exists then increase the number of copies
    public  void addABook(Book book){
        Book currentBook = serachABookByTitle(book.getBookTitle());
        if(currentBook!=null) {
            currentBook.setNoOfCopies(currentBook.getNoOfCopies()+1);
            return;
        }

        booksInLibrary.add(book);
    }


//  remove a book from library if not in library then inform to the user
    public  void deleteABook(String bookTitle){
        Book book = serachABookByTitle(bookTitle);

        if(book==null){
            System.out.println("Book by the name : "+bookTitle+" not present in our library..");
            return;
        }

        booksInLibrary.remove(book);

    }

//borrowing a book from library
    public  void borrowABook(Borrower borrower,Book book){
        if(book.getNoOfCopies()<1){
            System.out.println("Books out of stock....");
            return;
        }

        borrower.getBorrowedBooks().add(book);
        book.getBorrowersHistory().add(borrower);
        book.setNoOfCopies(book.getNoOfCopies()-1);

    }


//    add a borrower
    public void addBorrower(Borrower borrower){
            for (Borrower borrower1 : borrowersInLibrary){
                if(borrower1.getId()==borrower.getId()){
                    System.out.println("borrower already present...");
                    return;
                }
            }

            borrowersInLibrary.add(borrower);

    }

    //getter for borrowrs list
    public List<Borrower> getBorrowersInLibrary() {
        return borrowersInLibrary;
    }

    //show books in library
    public  void showAllBooks(){
        for(Book book : booksInLibrary){
            System.out.println(book);
        }
    }




    //todo:method to record fine of borrowers






}
