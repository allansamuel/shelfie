package com.shelfie.model;

public class ChildUnlockedBook {

    private Integer childUnlockedBookId;
    private ChildProfile childProfile;
    private InteractiveBook interactiveBook;

    public ChildUnlockedBook() {
    }

    public ChildUnlockedBook(Integer childUnlockedBookId, ChildProfile childProfile, InteractiveBook interactiveBook) {
        this.childUnlockedBookId = childUnlockedBookId;
        this.childProfile = childProfile;
        this.interactiveBook = interactiveBook;
    }

    public Integer getChildUnlockedBookId() {
        return childUnlockedBookId;
    }

    public void setChildUnlockedBookId(Integer childUnlockedBookId) {
        this.childUnlockedBookId = childUnlockedBookId;
    }

    public ChildProfile getChildProfile() {
        return childProfile;
    }

    public void setChildProfile(ChildProfile childProfile) {
        this.childProfile = childProfile;
    }

    public InteractiveBook getInteractiveBook() {
        return interactiveBook;
    }

    public void setInteractiveBook(InteractiveBook interactiveBook) {
        this.interactiveBook = interactiveBook;
    }
}
