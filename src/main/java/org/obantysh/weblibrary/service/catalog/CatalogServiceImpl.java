package org.obantysh.weblibrary.service.catalog;


import org.obantysh.weblibrary.dao.BookDao;
import org.obantysh.weblibrary.model.Catalog;
import org.obantysh.weblibrary.service.BookService;
import org.obantysh.weblibrary.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Autowired
    BookService bookService;

    @Autowired
    BookDao bookDao;

    @Override
    public Catalog handleCatalog(Catalog catalog) {
        if (catalog != null && !catalog.isEmpty()) {
            catalog.getBooks().forEach(bookService::updateBook);
        }
        return bookDao.getCatalog();
    }
}
