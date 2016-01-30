package org.obantysh.weblibrary.service;


import org.obantysh.weblibrary.model.Book;

import java.util.List;

public interface BookService {
    public Book getBookByID(String id);
    public List<Book> getBookList();
    public Book updateBook(Book book);
}
