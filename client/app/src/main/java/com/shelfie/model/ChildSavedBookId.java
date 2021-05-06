package com.shelfie.model;

import java.io.Serializable;

public class ChildSavedBookId implements Serializable {

    private Integer interactiveBookId;
    private Integer childProfileId;

    public ChildSavedBookId() {
    }

    public ChildSavedBookId(Integer interactiveBookId, Integer childProfileId) {
        this.interactiveBookId = interactiveBookId;
        this.childProfileId = childProfileId;
    }

    public Integer getInteractiveBookId() {
        return interactiveBookId;
    }

    public void setInteractiveBookId(Integer interactiveBookId) {
        this.interactiveBookId = interactiveBookId;
    }

    public Integer getChildProfileId() {
        return childProfileId;
    }

    public void setChildProfileId(Integer childProfileId) {
        this.childProfileId = childProfileId;
    }
}
