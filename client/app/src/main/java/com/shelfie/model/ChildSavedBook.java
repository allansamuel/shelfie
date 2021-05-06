package com.shelfie.model;

import java.io.Serializable;

public class ChildSavedBook implements Serializable {

    private ChildSavedBookId childSavedBookId;
    private ChildProfile childProfile;
    private InteractiveBook interactiveBook;
    private Integer chapterId;

    public ChildSavedBook() {
    }

    public ChildSavedBook(ChildSavedBookId childSavedBookId, ChildProfile childProfile,
                          InteractiveBook interactiveBook, Integer chapterId) {
        this.childSavedBookId = childSavedBookId;
        this.childProfile = childProfile;
        this.interactiveBook = interactiveBook;
        this.chapterId = chapterId;
    }

    public ChildSavedBookId getChildSavedBookId() {
        return childSavedBookId;
    }

    public void setChildSavedBookId(ChildSavedBookId childSavedBookId) {
        this.childSavedBookId = childSavedBookId;
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

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }
}
