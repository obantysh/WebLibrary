package org.obantysh.weblibrary.model;


import org.obantysh.weblibrary.utils.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

    @XmlID
    @XmlAttribute(name = "id")
    private String id;

    private String author;
    private String title;
    private String genre;
    private Float price;

    @XmlElement(name = "publish_date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date publishDate;

    private String description;

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEmpty() {
        return author == null && title == null && genre == null && price == null && publishDate == null && description == null;
    }
}
