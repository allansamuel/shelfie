package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chapters")
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title", length = 255)
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;

	public Chapter() {
		super();
	}

	public Chapter(Integer id, String title, InteractiveBook interactiveBook) {
		super();
		this.id = id;
		this.title = title;
		this.interactiveBook = interactiveBook;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public InteractiveBook getInteractiveBook() {
		return interactiveBook;
	}

	public void setInteractiveBook(InteractiveBook interactiveBook) {
		this.interactiveBook = interactiveBook;
	}
}
