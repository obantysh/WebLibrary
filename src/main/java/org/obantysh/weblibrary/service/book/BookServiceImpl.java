package org.obantysh.weblibrary.service.book;


import org.apache.log4j.Logger;
import org.obantysh.weblibrary.dao.BookDao;
import org.obantysh.weblibrary.model.Book;
import org.obantysh.weblibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger LOG = Logger.getLogger(BookServiceImpl.class);

    @Autowired
    BookDao bookDao;

    @Override
    public Book getBookByID(String id) {
        return bookDao.getBookById(id);
    }

    @Override
    public List<Book> getBookList() {
        return bookDao.getBooks();
    }

    @Override
    public Book updateBook(Book book) {
        if (book.isEmpty()) {
            return bookDao.deleteBookById(book.getId());
        } else {
            return bookDao.saveBook(book);
        }
    }
}
