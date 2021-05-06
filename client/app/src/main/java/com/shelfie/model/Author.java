package com.shelfie.model;

import java.io.Serializable;

public class Author implements Serializable {

    private Integer authorId;
    private String authorName;

    public Author() {
    }

    public Author(Integer authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
