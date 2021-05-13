package com.shelfie.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "interactive_books")
public class InteractiveBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "interative_book_id")
	private Integer interactiveBookId;
	
	@Lob
	@Column (name = "book_cover")
	private byte[] bookCover;
	
	@Column (name = "sinopsys", length = 800)
	private String sinopsys;

	@NotNull
	@Column (name = "publish_date")
	private Date publishDate;
	
	@NotNull
	@Column (name = "price")
	private Integer price = 0;
	
	@NotNull
	@Column (name = "title", length = 255)
	private String title;

	@OneToMany(
			mappedBy = "interactiveBook",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	        )
	private List<Chapter> chapters;
	
	@OneToMany(
			mappedBy = "interactiveBook",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	        )
	private List<Character> characters;
	
	@OneToMany(
			mappedBy = "interactiveBook",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	        )
	private List<Quest> quests;
	
	@ManyToMany
	@JoinTable(
			name = "book_categories", 
			joinColumns = {@JoinColumn(name = "interactive_book_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "category_id")}
			)
	private List<Category> bookCategories;
	
	@OneToMany(
			mappedBy = "interactiveBook",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true
	        )
	private List<ChildSavedBook> childSavedBooks;

	@ManyToMany
	@JoinTable(
			name = "book_authors", 
			joinColumns = {@JoinColumn(name = "interactive_book_id")}, 
			inverseJoinColumns = {@JoinColumn(name = "author_id")}
			)
	private List<Author> bookAuthors;
	
	public InteractiveBook() {
		super();
	}

	public InteractiveBook(Integer interactiveBookId, byte[] bookCover, String sinopsys, Date publishDate, Integer price,
			String title, List<Chapter> chapters, List<Character> characters, List<Quest> quests,
			List<Category> bookCategories, List<Author> bookAuthors) {
		super();
		this.interactiveBookId = interactiveBookId;
		this.bookCover = bookCover;
		this.sinopsys = sinopsys;
		this.publishDate = publishDate;
		this.price = price;
		this.title = title;
		this.chapters = chapters;
		this.characters = characters;
		this.quests = quests;
		this.bookCategories = bookCategories;
		this.bookAuthors = bookAuthors;
	}

	public Integer getInteractiveBookId() {
		return interactiveBookId;
	}

	public void setInteractiveBookId(Integer interactiveBookId) {
		this.interactiveBookId = interactiveBookId;
	}

	public byte[] getBookCover() {
		return bookCover;
	}

	public void setBookCover(byte[] bookCover) {
		this.bookCover = bookCover;
	}

	public String getSinopsys() {
		return sinopsys;
	}

	public void setSinopsys(String sinopsys) {
		this.sinopsys = sinopsys;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}

	public List<Category> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(List<Category> bookCategories) {
		this.bookCategories = bookCategories;
	}

	public List<Author> getBookAuthors() {
		return bookAuthors;
	}

	public void setBookAuthors(List<Author> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}
	
}
