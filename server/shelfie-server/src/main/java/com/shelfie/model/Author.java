package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "author")
public class Author {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Integer id;
	
	@Column (name = "author_name")
	private String authorName;

	public Author() {
		super();
	}

	public Author(Integer id, String authorName) {
		super();
		this.id = id;
		this.authorName = authorName;
	}

	public Integer getAuthorId() {
		return id;
	}

	public void setAuthorId(Integer id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
}
