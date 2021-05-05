package com.shelfie.model;

import java.io.Serializable;

public class Category implements Serializable {

    private Integer categoryId;
    private String categoryName;

    public Category() {
        super();
    }

    public Category(Integer categoryId, String categoryName) {
        super();
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
