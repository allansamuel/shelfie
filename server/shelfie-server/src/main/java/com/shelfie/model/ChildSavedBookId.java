package com.shelfie.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChildSavedBookId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "child_profile_id")
	private Integer childProfileId;
	
	@Column(name = "interactive_book_id")
	private Integer interactiveBookId;
	
	public ChildSavedBookId() {
		super();
	}

	public ChildSavedBookId(Integer childProfileId, Integer interactiveBookId) {
		super();
		this.childProfileId = childProfileId;
		this.interactiveBookId = interactiveBookId;
	}

	public Integer getChildProfileId() {
		return childProfileId;
	}

	public void setChildProfileId(Integer childProfileId) {
		this.childProfileId = childProfileId;
	}

	public Integer getInteractiveBookId() {
		return interactiveBookId;
	}

	public void setInteractiveBookId(Integer interactiveBookId) {
		this.interactiveBookId = interactiveBookId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ChildSavedBookId that = (ChildSavedBookId) o;
        return Objects.equals(childProfileId, that.childProfileId) &&
               Objects.equals(interactiveBookId, that.interactiveBookId);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(childProfileId, interactiveBookId);
    }
}
