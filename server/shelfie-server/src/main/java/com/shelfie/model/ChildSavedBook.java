package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "child_saved_books")
public class ChildSavedBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_saved_book_id")
	private Integer childSavedBookId;
	
	@ManyToOne
	@JoinColumn(name = "child_profile_id")
	private ChildProfile childProfile;
	
	@ManyToOne
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;
	
	@Column(name = "chapter_id")
	private Integer chapterId;

	public ChildSavedBook() {
		super();
	}

	public ChildSavedBook(Integer childSavedBookId, ChildProfile childProfile, InteractiveBook interactiveBook,
			Integer chapterId) {
		super();
		this.childSavedBookId = childSavedBookId;
		this.childProfile = childProfile;
		this.interactiveBook = interactiveBook;
		this.chapterId = chapterId;
	}

	public Integer getChildSavedBookId() {
		return childSavedBookId;
	}

	public void setChildSavedBookId(Integer childSavedBookId) {
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
