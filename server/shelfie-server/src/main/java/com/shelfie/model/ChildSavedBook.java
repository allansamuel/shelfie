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
	@MapsId("id")
	@JoinColumn(name = "id")
	private ChildProfile childProfile;
	
	@ManyToOne
	@MapsId("id")
	@JoinColumn(name = "id")
	private InteractiveBook interactiveBook;
	
	@Column(name = "id")
	private Integer id;

	public ChildSavedBook() {
		super();
	}

	public ChildSavedBook(ChildSavedBookId childSavedBookId, ChildProfile childProfile, InteractiveBook interactiveBook,
			Integer id) {
		super();
		this.childSavedBookId = childSavedBookId;
		this.childProfile = childProfile;
		this.interactiveBook = interactiveBook;
		this.id = id;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
