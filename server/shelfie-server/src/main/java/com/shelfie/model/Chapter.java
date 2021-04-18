package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chapters")
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chapter_id")
	private Integer chapterId;
	
	@Column(name = "title", length = 255)
	private String title;
	
	@ManyToOne
	@Column(name = "interactive_book_id")
	private InteractiveBook interactiveBook;

	public Chapter() {
		super();
	}

	public Chapter(Integer chapterId, String title, InteractiveBook interactiveBook) {
		super();
		this.chapterId = chapterId;
		this.title = title;
		this.interactiveBook = interactiveBook;
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
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
