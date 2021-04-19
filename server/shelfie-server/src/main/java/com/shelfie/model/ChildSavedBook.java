package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "child_saved_books")
public class ChildSavedBook {
	
	@EmbeddedId
	private ChildSavedBookId childSavedBookId;
	
	@ManyToOne
	@MapsId("childProfileId")
	@JoinColumn(name = "child_profile_id")
	private ChildProfile childProfile;
	
	@ManyToOne
	@MapsId("interactiveBookId")
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;
	
	@Column(name = "chapter_id")
	private Integer chapterId;

	public ChildSavedBook() {
		super();
	}

	public ChildSavedBook(ChildSavedBookId childSavedBookId, ChildProfile childProfile, InteractiveBook interactiveBook,
			Integer chapterId) {
		super();
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
