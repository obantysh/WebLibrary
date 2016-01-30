package org.obantysh.weblibrary.dao.xml;


import org.apache.log4j.Logger;
import org.obantysh.weblibrary.dao.BookDao;
import org.obantysh.weblibrary.model.Book;
import org.obantysh.weblibrary.model.Catalog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class XMLBookDao implements BookDao {
    private static final Logger LOG = Logger.getLogger(XMLBookDao.class);
    private Catalog currentCatalog;

    @Value("#{props.main_file_path}")
    private String filePath;

    @Override
    public Book saveBook(Book book) {
        Book bookToReplace = getBookById(book.getId());
        boolean fileModified;
        if (bookToReplace != null && !bookToReplace.equals(book)) {
            fileModified = Collections.replaceAll(currentCatalog.getBooks(), bookToReplace, book);
        } else {
            fileModified = currentCatalog.getBooks().add(book);
        }
        if (fileModified) {
            exportToFile();
        }
        return book;
    }

    @Override
    public Book getBookById(String id) {
        if (currentCatalog == null) {
            importFromFile();
        }
        if (currentCatalog == null) {
            return null;
        }
        for (Book book : currentCatalog.getBooks()) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Book deleteBookById(String id) {
        Book bookToDelete = getBookById(id);
        if (bookToDelete != null) {
            currentCatalog.getBooks().remove(bookToDelete);
            exportToFile();
        }
        return bookToDelete;
    }

    @Override
    public List<Book> getBooks() {
        if (currentCatalog == null) {
            importFromFile();
        }
        if (currentCatalog == null) {
            return new ArrayList<>();
        }
        return currentCatalog.getBooks();
    }

    @Override
    public Catalog getCatalog() {
        if (currentCatalog == null) {
            importFromFile();
        }
        return currentCatalog;
    }

    private void importFromFile() {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            currentCatalog = (Catalog) jaxbUnmarshaller.unmarshal(file);

            LOG.info("Successfully loaded catalog from file");
        } catch (JAXBException e) {
            LOG.error("Error during loading catalog from file: ", e);
        }
    }

    private void exportToFile() {
        try {
            File file = new File(filePath);
            JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(currentCatalog, file);

            LOG.info("Successfully saved catalog to file");
        } catch (JAXBException e) {
            LOG.error("Error during saving catalog to file: ", e);
        }
    }
}
