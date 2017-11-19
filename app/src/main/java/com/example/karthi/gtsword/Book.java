package com.example.karthi.gtsword;

/**
 * Created by Karthi on 11-Sep-17.
 */

import java.util.List;

public class Book {
    String bookName;
    List<Chapter> chapters;
    Book(String b, List<Chapter> c){
        bookName = b;
        chapters = c;
    }
    Book(Book B){
        bookName = B.bookName;
        chapters = B.chapters;
    }

    public Book() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}