package com.shelfie.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "category_name", length = 255)
	private String categoryName;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToMany(mappedBy = "bookCategories")
	List<InteractiveBook> interactiveBooks;

	public Category() {
		super();
	}

	public Category(Integer categoryId, String categoryName, List<InteractiveBook> interactiveBooks) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.interactiveBooks = interactiveBooks;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<InteractiveBook> getInteractiveBooks() {
		return interactiveBooks;
	}

	public void setInteractiveBooks(List<InteractiveBook> interactiveBooks) {
		this.interactiveBooks = interactiveBooks;
	}
}
