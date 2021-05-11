package com.shelfie.model;

import java.io.Serializable;
import java.util.Date;

public class InteractiveBook implements Serializable {

    private Integer interactiveBookId;
    private String bookCover;
    private String sinopsys;
    private Date publishDate;
    private Integer price;
    private String title;

    public InteractiveBook() {
    }

    public InteractiveBook(Integer interactiveBookId, String bookCover, String sinopsys, Date publishDate, Integer price, String title) {
        this.interactiveBookId = interactiveBookId;
        this.bookCover = bookCover;
        this.sinopsys = sinopsys;
        this.publishDate = publishDate;
        this.price = price;
        this.title = title;
    }

    public Integer getInteractiveBookId() {
        return interactiveBookId;
    }

    public void setInteractiveBookId(Integer interactiveBookId) {
        this.interactiveBookId = interactiveBookId;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getSinopsys() {
        return sinopsys;
    }

    public void setSinopsys(String interactiveBookSinopsys) {
        this.sinopsys = interactiveBookSinopsys;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
