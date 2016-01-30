package org.obantysh.weblibrary.dao;


import org.obantysh.weblibrary.model.Book;
import org.obantysh.weblibrary.model.Catalog;

import java.util.List;

public interface BookDao {
    public Book saveBook(Book book);
    public Book getBookById(String id);
    public Book deleteBookById(String id);
    public List<Book> getBooks();
    public Catalog getCatalog();
}
