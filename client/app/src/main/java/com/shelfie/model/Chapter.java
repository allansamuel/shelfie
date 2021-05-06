package com.shelfie.model;

import java.io.Serializable;

public class Chapter implements Serializable {

    private Integer chapterId;
    private String title;
    private InteractiveBook interactiveBook;

    public Chapter() {
    }

    public Chapter(Integer chapterId, String title, InteractiveBook interactiveBook) {
        this.chapterId = chapterId;
        this.title = title;
        this.interactiveBook = interactiveBook;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InteractiveBook getInteractiveBook() {
        return interactiveBook;
    }

    public void setInteractiveBook(InteractiveBook interactiveBook) {
        this.interactiveBook = interactiveBook;
    }
}
