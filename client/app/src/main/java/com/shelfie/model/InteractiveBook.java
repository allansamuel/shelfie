package com.shelfie.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InteractiveBook implements Serializable {

    private Integer interactiveBookId;
    private String bookCover;
    private String sinopsys;
    private Date publishDate;
    private Integer price;
    private String title;
    private List<Chapter> chapters;
    private List<Character> characters;
    private List<Quest> quests;
    private List<Category> bookCategories;
    private List<ChildSavedBook> childSavedBooks;
    private List<Author> bookAuthors;

    public InteractiveBook() {
    }

    public InteractiveBook(Integer interactiveBookId, String bookCover, String sinopsys, Date publishDate, Integer price, String title, List<Chapter> chapters, List<Character> characters, List<Quest> quests, List<Category> bookCategories, List<ChildSavedBook> childSavedBooks, List<Author> bookAuthors) {
        this.interactiveBookId = interactiveBookId;
        this.bookCover = bookCover;
        this.sinopsys = sinopsys;
        this.publishDate = publishDate;
        this.price = price;
        this.title = title;
        this.chapters = chapters;
        this.characters = characters;
        this.quests = quests;
        this.bookCategories = bookCategories;
        this.childSavedBooks = childSavedBooks;
        this.bookAuthors = bookAuthors;
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

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public List<Category> getBookCategories() {
        return bookCategories;
    }

    public void setBookCategories(List<Category> bookCategories) {
        this.bookCategories = bookCategories;
    }

    public List<ChildSavedBook> getChildSavedBooks() {
        return childSavedBooks;
    }

    public void setChildSavedBooks(List<ChildSavedBook> childSavedBooks) {
        this.childSavedBooks = childSavedBooks;
    }

    public List<Author> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookAuthors(List<Author> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
