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
@Table(name = "child_unlocked_books")
public class ChildUnlockedBook {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_unlocked_book_id")
	private Integer childUnluckedBookId;
	
	@ManyToOne
	@JoinColumn(name = "child_profile_id")
	private ChildProfile childProfile;
	
	@ManyToOne
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;

	public ChildUnlockedBook() {
		super();
	}

	public ChildUnlockedBook(Integer childUnluckedBookId, ChildProfile childProfile, InteractiveBook interactiveBook) {
		super();
		this.childUnluckedBookId = childUnluckedBookId;
		this.childProfile = childProfile;
		this.interactiveBook = interactiveBook;
	}

	public Integer getChildUnluckedBookId() {
		return childUnluckedBookId;
	}

	public void setChildUnluckedBookId(Integer childUnluckedBookId) {
		this.childUnluckedBookId = childUnluckedBookId;
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
