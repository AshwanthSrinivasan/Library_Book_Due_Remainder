package com.example.librarybookdueremainder;

public class Book {

    private String bookName;
    private String returnDate;

    public Book() {}

    public Book(String bookName, String returnDate) {
        this.bookName = bookName;
        this.returnDate = returnDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

}
